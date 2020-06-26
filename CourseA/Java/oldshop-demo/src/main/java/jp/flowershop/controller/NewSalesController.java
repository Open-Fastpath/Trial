package jp.flowershop.controller;

import jp.core.controller.InterfaceData;
import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;

import jp.flowershop.application.SalesApplicationImpl;
import jp.flowershop.domain.Sales;

public class NewSalesController extends WebController{

    @Override
    protected String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {

        try{
            SalesApplicationImpl app = new SalesApplicationImpl();

            //新たに販売伝票番号を採番し販売明細一覧へ
            Sales sales = app.newSales();

            return "salesdetaillist?salesNo=" + sales.getSalesNo();        
        }
        catch(SystemException e){
            throw e;
        }
    }
}