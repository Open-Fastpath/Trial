package jp.core.controller;

import java.util.logging.Logger;

import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;

public abstract class WebController {

    private Logger logger = null;

    protected WebController() {

        logger = Logger.getLogger(this.getClass().getName());
    
    }

    public String run(InterfaceData inputData, InterfaceData viewData, InterfaceData stateData)
    throws Exception {

        try{
            logger.info("START Controller:" + this.getClass().getName());

            String viewName = service(inputData, viewData, stateData);

            logger.info("END   Controller:" + this.getClass().getName() + " viewName:" + viewName);
            
            return viewName;
        }
        catch(Exception e){
            logger.info("ERROR Controller:" + this.getClass().getName());
            throw e;
        }     
    }

    protected abstract String service(InterfaceData inputData, 
                                      InterfaceData viewData, 
                                      InterfaceData stateData) 
    throws InputException, StatusException, SystemException;

}