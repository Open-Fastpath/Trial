package jp.flowershop.controller;

import jp.core.controller.InterfaceData;
import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;

import jp.flowershop.application.SalesApplicationImpl;

public class CancelSalesController extends WebController{

    @Override
    protected String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {

        try{
            SalesApplicationImpl app = new SalesApplicationImpl();

            String salesNo = input.getString("cancelSalesNo");

            app.cancelSales(salesNo);

            return "saleslist";        
        }
        catch(SystemException e){
            throw e;
        }
    }
}