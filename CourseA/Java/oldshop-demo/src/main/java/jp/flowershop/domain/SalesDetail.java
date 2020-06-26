package jp.flowershop.domain;

import lombok.Getter;
import lombok.Setter;

public class SalesDetail{
    
    @Getter
    @Setter
    private String salesNo;
    @Getter
    @Setter
    private String salesDetaillNo; 
    @Getter
    @Setter
    private String productId;
    @Getter
    @Setter
    private String productName;
    @Getter
    @Setter
    private Integer unitPrice;
    @Getter
    @Setter
    private Integer qty = 0;
    @Getter
    @Setter
    private Integer amount = 0;

    public SalesDetail(String salesNo, 
                       String salesDetaillNo,
                       String productId, 
                       String productName,
                       int unitPrice,
                       int qty){    

        this.salesNo = salesNo;
        this.salesDetaillNo = salesDetaillNo;
        this.productId = productId;
        this.productName  = productName;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.amount = qty * unitPrice;

    }

    public void addQty(int increased) {
        this.qty += increased;
        reCalc();
    }

    public void reduceQty(int decreased) {
        this.qty -= decreased;
        reCalc();
    }

    public void changeQty(int newQty) {
        this.qty = newQty;
        reCalc();
    }

    private void reCalc() {
        this.amount = this.qty * this.unitPrice;
    }
}