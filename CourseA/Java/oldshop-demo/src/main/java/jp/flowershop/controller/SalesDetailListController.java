package jp.flowershop.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import jp.core.controller.InterfaceData;
import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;

import jp.flowershop.application.SalesApplicationImpl;
import jp.flowershop.domain.Sales;

public class SalesDetailListController extends WebController{

    @Override
    protected String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {

        SalesApplicationImpl app = new SalesApplicationImpl();

        try{
            String salesNo = input.getString("salesNo");

            Sales sales = app.showSales(salesNo);

            viewInterfaceData.set("salesNo", sales.getSalesNo());
            viewInterfaceData.set("salesDate", sales.getSalesDate());
            viewInterfaceData.set("totalAmount", 
                                  NumberFormat.getNumberInstance().format(sales.getTotalAmount()));
            viewInterfaceData.set("salesdetaillist", sales.getSalesDetailList());
        }
        catch(Exception e){
            List<Sales> dummyList = new ArrayList<>();
            dummyList.add(new Sales());
            viewInterfaceData.set("errorMessage", e.getMessage());
            viewInterfaceData.set("saleslist", dummyList);
        }

        return "salesdetaillistview";        
    }
}


