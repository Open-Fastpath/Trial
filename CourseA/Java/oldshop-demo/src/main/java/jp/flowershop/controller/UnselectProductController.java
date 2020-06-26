package jp.flowershop.controller;


import jp.core.controller.InterfaceData;
import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;

import jp.flowershop.application.SalesApplicationImpl;
import jp.flowershop.domain.Sales;

public class UnselectProductController extends WebController{

    @Override
    protected String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {

        try{
            SalesApplicationImpl app = new SalesApplicationImpl();

            String salesNo = input.getString("salesNo");
            String productId = input.getString("productId");

            app.removeSalesDetail(salesNo, productId);

            return "salesdetaillist?salesNo=" + salesNo;
        }
        catch(InputException ie){
            return "saleslist?errorMessage=" + ie.getMessage();
        }
        catch(StatusException se){
            return "saleslist?errorMessage=" + se.getMessage();
        }
    }
}