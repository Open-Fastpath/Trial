package jp.flowershop.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StatusException extends Exception{

    private static final long serialVersionUID = -742146227939251653L;
    private static Logger logger = Logger.getLogger(StatusException.class.getName());

    public StatusException(String message){
        super(message);
        logger.warning(message);
    }

    public StatusException(String message, Throwable t){
        super(message, t);
        logger.log(Level.WARNING, message, t);
    }
}