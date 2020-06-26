package jp.core.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemException extends Exception{

    private static final long serialVersionUID = 2246930402748037890L;
    private static Logger logger = Logger.getLogger(SystemException.class.getName());

    public SystemException(final String message, final Throwable t) {
        super(message, t);
        logger.log(Level.SEVERE, message, t);
    }
}