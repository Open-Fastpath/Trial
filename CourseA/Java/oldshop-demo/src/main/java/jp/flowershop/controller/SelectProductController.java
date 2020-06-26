package jp.flowershop.controller;


import jp.core.controller.InterfaceData;
import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;

import jp.flowershop.application.SalesApplicationImpl;
import jp.flowershop.domain.Sales;

public class SelectProductController extends WebController{

    @Override
    protected String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {

        String salesNo = "";
        try{
            SalesApplicationImpl app = new SalesApplicationImpl();

            salesNo = input.getString("salesNo");

            String productId = input.getString("productId");

            int qty = Integer.valueOf(input.getString("qty"));
            int unitPrice = Integer.valueOf(input.getString("unitPrice"));

            app.selectProduct(salesNo, productId, unitPrice, qty);

            return "salesdetaillist?salesNo=" + salesNo;

        }catch(NumberFormatException nume){
            return "productselection?salesNo=" + salesNo + "&error=noqty";
        }
        catch(InputException ie){
            return "productselection?salesNo=" + salesNo + "&error=noproductid";
        }
        catch(StatusException se){
            return "productselection?salesNo=" + salesNo + "&error=alreadyconfirmed";
        }
    }
}