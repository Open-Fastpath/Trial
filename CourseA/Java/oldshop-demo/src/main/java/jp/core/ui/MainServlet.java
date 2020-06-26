package jp.core.ui;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.core.controller.WebController;
import jp.core.controller.WebControllerFactory;
import jp.core.config.IApplicationConfigurator;
import jp.core.controller.InterfaceData;

public class MainServlet extends HttpServlet{

    private static final long serialVersionUID = 10000;
    
    private static final String CONFIG_CLASS_PARAM = "InitConfigurator";

    private static final String HTTP_GET = "GET";

    private Logger logger = Logger.getLogger(MainServlet.class.getName());

    /**
     * <p>初期化処理</p>
     * web.xmlの<init-param>で指定されたアプリケーション設定クラスを実行し
     * Dependency Injectionによるアプリケーションの設定や環境ごとの切替を実現する
     * ・URIアクションに対して実行するControllerクラスを指定する
     * ・Applicationクラスが実行するRepogitryクラスを指定する
     */
    @Override
    public void init()
    throws ServletException {

        logger = Logger.getLogger(MainServlet.class.getName());

        String configuratorName = getServletConfig().getInitParameter(CONFIG_CLASS_PARAM);

        try{
            logger.info(" ApplicationConfigurator:" + configuratorName +  "でControllerとRepositoryの設定を開始します ");

            IApplicationConfigurator configurator
             = (IApplicationConfigurator)Class.forName(configuratorName)
                .getDeclaredConstructor().newInstance();

            configurator.config();
            logger.info(" ApplicationConfigurator:" + configuratorName +  " 設定を完了しました");
            
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "ERROR ApplicationConfigurator:" + configuratorName, e);
            //処理は続行する
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        try{
            //アクセスログを記録
            logAccess(request, response);

            //Webのリクエストをコントローラ入力用のオブジェクトに変換する
            InterfaceData inputData = getinputData(request);
            //コントローラが表示用に編集してビューに渡すデータの格納場所を作る
            InterfaceData viewData = new InterfaceData();
            //リクエストを横断してのデータ保持はsessionに保持する。
            InterfaceData state = getState(request.getSession());
            
            //URIに応じてコントローラに処理を委ねる
            String viewName = runController(request, inputData, viewData, state);        

            //GETは情報表示にてそのままHTTPのページを生成
            if (request.getMethod().equals(HTTP_GET)){
                viewPage(request, response, viewName, viewData);
            }
            //POST PUT DELETEは更新アクションの後にGETの表示ページにリダイレクト
            else{
                sendRedirect(request, response, viewName, viewData);
            }
        }
        catch(ServletException se){
            logger.log(Level.SEVERE, "ERROR ", se);
            throw se;
        }
        catch(IOException ioe){
            logger.log(Level.SEVERE, "ERROR ", ioe);
            throw ioe;
        }     
        catch(Exception e){
            logger.log(Level.SEVERE, "ERROR ", e);
            throw new ServletException(e.getMessage());
        }
    
    }

    private InterfaceData getinputData(HttpServletRequest request)
    throws Exception{

        InterfaceData inputData = new InterfaceData();

        // POST時にformのinputとして渡されるパラメータをInputモデルにセットする。
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()){
            String key = (String)keys.nextElement();
            String vals[] = request.getParameterValues(key);
            
            if(vals.length == 1){
                inputData.set(key, getSanitizedValue(vals[0]));
            }
            else{
                inputData.set(key, vals);
            }
        }

        // http://abc.com/contextpath/action?param1=value1&param2&value2として
        // ? 以降に渡されるQUERYSTRINGのキーと値を分解してInputモデルにセットする。

        //最初にQUERYSTRINGを＆で分解し次に=で分解しキーと値に分けてモデルに登録
        String queryString = request.getQueryString();

        if (request.getMethod().equals(HTTP_GET) && queryString != null) {
            String parameterList[] = request.getQueryString().split("&");
            
            for (int i = 0; i < parameterList.length; i++) {
                
                String keyValue[] = parameterList[i].split("=");
                
                //クロスサイト対策として特殊文字を変換（サニタイズ）して安全な値を設定
                inputData.set(keyValue[0], getSanitizedValue(keyValue[1]));
            }
        }

        return inputData;
    }

    private InterfaceData getState(HttpSession session){

        InterfaceData state = new InterfaceData();

        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()){
            String key = (String)keys.nextElement();
            String vals[] = (String[])session.getAttribute(key);
            
            if(vals.length == 1){
                state.set(key, vals[0]);
            }
            else{
                state.set(key, vals);
            }
        }
        return state;
    }

    private String getSanitizedValue(String plainValue) {

        if (plainValue == null) return plainValue;

        String sanitized = plainValue.replace("&", "&amp;")
                                     .replace("\"", "&quot;")
                                     .replace("<", "&lt;")
                                     .replace(">", "&gt;")
                                     .replace("'", "&#39;");
        return sanitized;
    }
    
    private String runController(HttpServletRequest request, InterfaceData inputData, InterfaceData viewData, InterfaceData state)
    throws Exception {

        try{
            //URIのパスをActionとして相対するControllerを起動する
            //定義はIApplicationConfiguratorを実装するクラスがweb.xmlの
            //MainServletのinit-paramで指定され、init()で実行される
            //存在しない場合はControllerFactoryが例外をスローする。
            WebController controller = 
                WebControllerFactory.getController(getAction(request));

            return controller.run(inputData, viewData, state);
        }   
        catch(Exception e){
            logger.log(Level.SEVERE, "ERROR ", e);
            throw e;
        }     

    }

    private String getAction(HttpServletRequest request){

        String uri = request.getRequestURI();

        String[] paths = uri.split("/");

        if ( paths.length < 3 ) return "auth";

        //URIのコンテキストルートの次を実行したいアクションをして取得する
        return paths[2];

    }

    private void viewPage(HttpServletRequest request, 
                          HttpServletResponse response,
                          String viewName, 
                          InterfaceData viewData) 
    throws ServletException, IOException{

        try {            
            request.setCharacterEncoding("UTF-8");

            //コントローラの実行結果をviewDataから取り出しビューにセット
            for (Map.Entry<String, Object> entry : viewData.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            } 

            //web.xmlの<servlet-name>の設定より名前で検索しJSPを実行する
            RequestDispatcher rd = request.getRequestDispatcher(viewName);
            rd.forward(request, response);

        } 
        catch (Exception e) {
            logger.log(Level.SEVERE, 
                       "ERROR [PAGE:" + viewName + "]", 
                       e);
            throw new ServletException(e.getMessage());
        }
    }

    private void sendRedirect(HttpServletRequest request, 
                              HttpServletResponse response, 
                              String viewName, 
                              InterfaceData viewData)
    throws ServletException, IOException{

        try{
            
            response.sendRedirect(request.getContextPath() + "/" + viewName);

        } 
        catch (Exception e) {
            logger.log(Level.SEVERE, 
                       "ERROR URI[" + request.getContextPath() + "/" + viewName + "]", 
                       e);
            throw new ServletException(e.getMessage());
        }

    }

    private void logAccess(HttpServletRequest request, HttpServletResponse response){
        
        StringBuffer message = new StringBuffer();
        
        message.append("REMOTE:" + request.getRemoteAddr() + " ");
        message.append("METHOD:" + request.getMethod() + " ");
        message.append("URL:" + request.getRequestURL() + " ");
        message.append("ROOT:" + request.getContextPath() + " ");
        message.append("URI:" + request.getRequestURI() + " ");
        message.append("PATH:" + request.getPathInfo() + " ");
        message.append("QUERYSTRING:" + request.getQueryString() + " ");
        message.append("SID:" + request.getRequestedSessionId() + " ");

        String key = null;

        message.append("HEADERS[");

        Enumeration<String> e = request.getHeaderNames();
        while(e.hasMoreElements()){
            key = (String) e.nextElement();
            message.append(key + ":" + request.getHeader(key));
        }

        message.append("]");
    
        message.append("PARAMS[");

        Map<String, String[]> paramsMap = request.getParameterMap();
        Iterator<String> it = paramsMap.keySet().iterator();

        while (it.hasNext()) {
            key = (String)it.next();
            String[] vals = (String[])paramsMap.get(key);
            
            for (int i = 0;i<vals.length;i++) {
                message.append(key + ":" + vals[i] + "|");
            }
        }

        message.append("]");

        logger.info("====== HTTP REQUEST START=====\n" + message + "\n====== HTTP REQUEST END=====");

    }

}

