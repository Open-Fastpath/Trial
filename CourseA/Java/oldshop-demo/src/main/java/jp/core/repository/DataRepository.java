package jp.core.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jp.core.exception.SystemException;

public abstract class DataRepository {
    
    private static String dataContext = null;
    private static Context ctx = null;
    private static DataSource ds = null;
    private Connection con = null;
    private PreparedStatement statement = null;
    protected ResultSet rs = null;
    private boolean isAutoCommit = false;

    private Logger logger = Logger.getLogger(DataRepository.class.getName());

    protected static void setDataContext(String contextPath)
    throws SystemException{
        
        dataContext = contextPath;

        try{
            ctx = new InitialContext();
            ds= (DataSource)ctx.lookup(contextPath);
        }
        catch(Exception e){
            throw new SystemException("データソースの初期化に失敗 lookup:[" + dataContext + "]", e);
        }
    }

    protected void find(String sql, Object[] params)
    throws SystemException {

        try{
            this.con = getConnection();

            this.statement = con.prepareStatement(sql);
            
            if (params != null){
                for (int i=0; i<params.length; i++){
                    if (params[i] instanceof String){
                        statement.setString(i+1, (String)params[i]);
                    }
                    else if(params[i] instanceof Integer) {
                        statement.setInt(i+1, (Integer)params[i]);
                    }
                    else {
                        throw new SystemException("SQL実行時のパラメータの型はStringまたはInteger型である必要があります。", 
                                                  new Exception("SQL Parameter型エラー"));
                    }
                }
            }

            this.rs = statement.executeQuery();
                
        }
        catch(Exception e){
            
            releaseReference();

            throw new SystemException("データソースの照会処理でエラーが発生しました "
                                    + "SQL[" + sql + "]", e);
        }
    }

    protected int execute(String sql, Object[] params)
    throws SystemException {

        try{
            con = getConnection();

            statement = con.prepareStatement(sql);
            
            if (params != null){
                for (int i=0; i<params.length; i++){
                    if (params[i] instanceof String){
                        statement.setString(i+1, (String)params[i]);
                    }
                    else if(params[i] instanceof Integer) {
                        statement.setInt(i+1, (Integer)params[i]);
                    }
                    else {
                        throw new SystemException("SQL実行時のパラメータの型はStringまたはInteger型である必要があります。", 
                                                  new Exception("SQL Parameter型エラー"));
                    }
                }
            }

            int savedCount = statement.executeUpdate();

            commit(); 

            return savedCount;
        }
        catch(Exception e){
            
            rollback(); 

            throw new SystemException("データソースの照会処理でエラーが発生しました "
                                    + "SQL[" + sql + "]", e);
        }
    }

    private Connection getConnection() throws SystemException    
    {
        Connection con = null;

        try{
            if(con != null){
                return con;
            }

            //DataSourceのコネクションプールが初期化されていなければ初期化する
            if (ds == null) {
                setDataContext(dataContext);
            } 

            if (dataContext == null) {
                throw new SystemException("DB接続エラー", 
                    new IllegalStateException("DataSourceのコンテキストがアプリケーションコンフィグで設定されていません"));
            }

            //コネクションプールよりデータを取得
            con = ds.getConnection();
            con.setAutoCommit(this.isAutoCommit);

            return con;
        }
        catch(Exception e){
            throw new SystemException("DB接続エラー", e);
        }
    }

    protected void commit(){

        try{
            if (con != null && isAutoCommit == false) {
                con.commit();
            }
        }
        catch(SQLException sqle){
            logger.log(Level.SEVERE, "コミット処理でエラーが発生しました", sqle);                
            //エラーは無視して進める
        }
    }
    protected void rollback(){

        try{
            if (con != null && isAutoCommit == false) {
                con.rollback();
            }
        }
        catch(SQLException sqle){
            logger.log(Level.SEVERE, "例外発生時のロールバック処理でエラーが発生しました", sqle);                
            //エラーは無視して進める
        }
    }

    protected void releaseReference(){

        try{
            if (rs != null){
                rs.close();
                rs = null;
            }
            if(statement != null){
                statement.close();
                statement = null;
            }
            if(con != null){
                con.close();
                con = null;
            }
        }
        catch(SQLException sqle){
            logger.log(Level.SEVERE, "DB照会リソースのクローズ処理でエラーが発生しました", sqle);                
            //エラーは無視して進める
        }    
    }

    protected void releaseExecutable(){

        try{
            if(statement != null){
                statement.close();
                statement = null;
            }
            if(con != null){
                con.close();
                con = null;
            }
        }
        catch(SQLException sqle){
            logger.log(Level.SEVERE, "DB実行リリースのクローズ処理でエラーが発生しました", sqle);                
            //エラーは無視して進める
        }    
    }

    protected void setAutoCommit(boolean mode)
    throws SystemException{

        try{
            con.setAutoCommit(mode);
            this.isAutoCommit = mode;
        }
        catch(SQLException sqle){
            throw new SystemException("コミットモードの変更にてエラーが発生しました。", sqle);
        }

    }
}