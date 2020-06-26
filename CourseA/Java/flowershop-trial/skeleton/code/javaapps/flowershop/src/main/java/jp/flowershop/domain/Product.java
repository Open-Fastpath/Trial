package jp.flowershop.domain;

import java.time.LocalDate;

import jp.flowershop.exception.InputException;
import jp.flowershop.exception.SystemException;
import jp.flowershop.util.DateTimeUtil;

public class Product {
    
    private static final String DEFAULT_ENDDATE = "99991231";

    private String productId = null;
    private String productName = null;
    private int unitPrice = 0;
    private LocalDate salesStartDate = null;
    private LocalDate salesEndDate = null;

    public Product(String productId, String productName, int unitPrice, String salesStartDate) 
    throws InputException{

        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;

        try {
            this.salesStartDate = DateTimeUtil.toDate(salesStartDate);
            this.salesEndDate = DateTimeUtil.toDate(DEFAULT_ENDDATE);    
        }
        catch(SystemException e){
            new InputException("販売開始日には有効な日付を指定ください", e);
        }
    }

    public void changeName(String newName) {
        this.productName = newName;
    }

    public void changeUnitPrice(int newUnitPrice) {
        this.unitPrice = newUnitPrice;
    }

    public String getProductId(){
        return this.productId;
    }

    public String getProductName(){
        return this.productName;
    }

    public int getUnitPrice(){
        return unitPrice;
    }

    public boolean isInvalid(){
        if (this.productId == null ||
            this.productName == null ||
            this.unitPrice == 0 ) return true;
        
        if (this.productId.isEmpty() || this.productName.isEmpty()) return true;
        
        return false;
    }

    public boolean isForSale(){

        LocalDate today = LocalDate.now();
        return salesStartDate.isAfter(today) && salesEndDate.isAfter(today);

    }
}
