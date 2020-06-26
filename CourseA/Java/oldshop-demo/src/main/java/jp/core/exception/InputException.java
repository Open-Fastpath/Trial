package jp.core.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InputException extends Exception{

    private static final long serialVersionUID = 1539793924355437262L;
    private static Logger logger = Logger.getLogger(InputException.class.getName());

    public InputException(String message){
        super(message);
        logger.info(message);
    }

    public InputException(String message, Throwable t){
        super(message, t);
        logger.log(Level.INFO, message, t);
    }
}
