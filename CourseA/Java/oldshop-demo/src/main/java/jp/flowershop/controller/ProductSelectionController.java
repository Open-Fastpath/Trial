package jp.flowershop.controller;

import java.util.List;

import jp.core.controller.InterfaceData;
import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;

import jp.flowershop.application.SalesApplicationImpl;
import jp.flowershop.domain.Product;
import jp.flowershop.domain.Sales;

public class ProductSelectionController extends WebController{

    @Override
    protected String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {

        SalesApplicationImpl app = new SalesApplicationImpl();

        String salesNo = input.getString("salesNo");

        //指定された販売伝票番号が存在するか確認
        Sales sales = app.showSales(salesNo);

        viewInterfaceData.set("salesNo", sales.getSalesNo());

        //販売できる商品一覧を取得
        List<Product> productList = app.showSalesProducts();

        viewInterfaceData.set("productlist", productList);

        String error = input.getString("error");
        if (error != null){
            StringBuffer message = new StringBuffer();
            if (error.equals("alreadyconfirmed")){
                message.append("すでに販売が完了しているため変更できません。");
            }
            if (error.equals("noproductid")){
                message.append("商品を選択してください。");
            }
            if (error.equals("noqty")){
                message.append("数量は必須です。数値で入力ください。");
            }
            viewInterfaceData.set("errorMessage", message.toString());
        }

        return "productselectionview";        
    }
}