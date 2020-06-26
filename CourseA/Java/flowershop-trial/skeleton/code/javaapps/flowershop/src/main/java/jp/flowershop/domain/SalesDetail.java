package jp.flowershop.domain;

// FOR JPA START
import java.io.Serializable;
// FOR JPA END

// FOR JPA START
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
// FOR JPA END

@Entity
public class SalesDetail implements Serializable {
    
    private static final long serialVersionUID = -297387477402407049L;

    @Getter
    @Setter 
    @Id 
    @Column(unique = true, nullable = false)
    private String salesDetailNo; 
    
    @Getter 
    @Setter 
    @Column(nullable = false)
    private String salesNo;

    @Getter 
    @Setter 
    @Column(nullable = false)
    private String productId;

    @Getter 
    @Setter 
    @Column(nullable = false)
    private String productName;

    @Getter 
    @Setter 
    @Column(nullable = false)
    private Integer unitPrice;

    @Getter 
    @Setter 
    @Column(nullable = false)
    private Integer qty = 0;

    @Getter
    @Setter 
    @Column(nullable = false)
    private Integer amount = 0;

    protected SalesDetail(){

    }

    public SalesDetail(String salesNo, 
                       String salesdetailNo,
                       String productId, 
                       String productName,
                       int unitPrice,
                       int qty){    

        this.salesNo = salesNo;
        this.salesDetailNo = salesdetailNo;
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