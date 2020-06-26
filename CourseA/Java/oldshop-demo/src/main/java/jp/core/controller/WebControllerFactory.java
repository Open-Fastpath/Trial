package jp.core.controller;

import java.util.HashMap;
import java.util.Map;

import jp.core.exception.SystemException;

public class WebControllerFactory {
    
    private static Map<String, String> controllers = new HashMap<>();

    public static <T extends WebController> void addController(String name, Class<T> clazz) {
        controllers.put(name, clazz.getName());
    }

    public static WebController getController(String action)
    throws SystemException{

        WebController controller = null;

        String controllerName = controllers.get(action);

        try {
            if (controllerName == null) throw new Exception("指定されたURIに対するControllerがありません");

            controller = 
                (WebController)Class.forName(controllerName)
                .getConstructor().newInstance();
        } 
        catch(Exception e){
            throw new SystemException("ERROR Controller起動不可 ACTION:" + action + " CONTROLLER:" + controllerName, e);
        }     

        return controller;

    }

    private WebControllerFactory(){}
}