package jp.flowershop.util;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.LogManager;

public class LoggerConfig{
    
    protected static final String LOGGING_PROPERTIES = "applogger.properties";    
    
    protected static final String LOGGING_PROPERTIES_DATA
    = "handlers=java.util.logging.ConsoleHandler\n"
    + ".level=FINEST\n"
    + "java.util.logging.ConsoleHandler.level=FINEST\n"
    + "java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter"
    + "";

    protected static String LOG_FORMAT
     = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$s %2$s %5$s%6$s%n";
    
     private static Logger logger = null;
    private static InputStream inStream = null;

    static {
        try{

            logger = Logger.getLogger(LoggerConfig.class.getName());


            System.setProperty("java.util.logging.SimpleFormatter.format",
                               LOG_FORMAT);
            
            inStream = new ByteArrayInputStream(LOGGING_PROPERTIES_DATA.getBytes("UTF-8"));

            //logger.info("ログ設定:" + LOGGING_PROPERTIES + "によりログを設定");
            //InputStream inStream = LoggerConfig.class.getClassLoader().getResourceAsStream(LOGGING_PROPERTIES);

            LogManager.getLogManager().readConfiguration(inStream);
            logger.config("ログ設定: LogManagerを設定しました。");
        } 
        catch (IOException e) {

            logger.warning("ログ設定: LogManager設定の際に例外が発生しました。:" + e.toString());

        } 
        finally {

            try {
                if (inStream != null) inStream.close();
            } 
            catch (IOException e) {
                logger.warning("ログ設定: ログ設定プロパティファイルのストリームクローズ時に例外が発生しました。:" + e.toString());
            }
        }
    }
}