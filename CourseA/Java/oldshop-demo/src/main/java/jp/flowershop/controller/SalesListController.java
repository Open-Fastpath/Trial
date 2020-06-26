package jp.flowershop.controller;

import java.util.List;

import jp.core.controller.InterfaceData;
import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;

import jp.flowershop.application.SalesApplicationImpl;
import jp.flowershop.domain.Sales;

public class SalesListController extends WebController{

    @Override
    protected String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {

        SalesApplicationImpl app = new SalesApplicationImpl();

        try{
            List<Sales> salesList = app.showSalesList();
            viewInterfaceData.set("saleslist", salesList);
        }
        catch(Exception e){
            viewInterfaceData.set("errorMessage", e.getMessage());
        }

        return "saleslistview";        
    }
}