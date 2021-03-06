package jp.flowershop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [FOR TRIAL]
 * 既存のPureJavaのドメインモデル（エンティティ）クラスをJPAで
 * リポジトリクラスが自動マッピングでデータベースからのデータ取得や更新が
 * 可能となるように設定ください。
 * 
 * （１）下記のオプジェクトを参照のためにimport宣言します。
 *      -------------------------
 *      java.io.Serializable;
 * 
 *      javax.persistence.Column;
 *      javax.persistence.Entity;
 *      javax.persistence.Id;
 *      javax.persistence.Transient;
 * 
 * （２）本クラスがJPAが参照するEntityオブジェクトであることを
 *      クラス宣言の前にアノテーションを付与しSpring Bootと
 *      Spring Data JPAに伝えます。
 *      -------------------------
 *      @Entity
 * 
 * （３）本クラスがデータ永続化可能であることを示すため
 *      Serializable インタフェースをクラス宣言で実装します。
 *      -------------------------
 *      implements Serializable
 * 
 * （４）下記のprivateメンバがJPAからアクセスできるようにするため
 *      アノテーションをメンバ変数の前に追記します。
 *       ・salesDate
 *       ・salesDateTime
 *       ・totalQty
 *       ・totalAmount
 *      -------------------------
 *      @Column(nullable = false)
 * 
 * （５）salesNoは一意のプライマリキーであることを示す
 *      アノテーションをメンバ変数の前に追記します。
 *      -------------------------
 *      @Id
 *      @Column(unique = true, nullable = false)
 * 
 * （６）上記以外のメンバ変数はデータの永続化対象外であるため
 *      永続化対象外のアノテーションをメンバ変数の前に追記します。
 *      -------------------------
 *       @Transient
 */

import lombok.Getter;
import lombok.Setter;

import jp.flowershop.exception.InputException;
import jp.flowershop.exception.StatusException;
import jp.flowershop.exception.SystemException;
import jp.flowershop.util.DateTimeUtil;

public class Sales {

    private static final long serialVersionUID = 4243393888959167053L;

    private static final String SALES_NO_PREFIX = "F";

    @Getter
    @Setter
    private String salesNo = null;

    @Getter
    @Setter
    private String salesDate = null;

    @Getter
    @Setter
    private String salesDateTime = null;

    @Getter
    @Setter
    private Integer totalQty = Integer.valueOf(0);

    @Getter
    @Setter
    private Integer totalAmount = Integer.valueOf(0);

    //販売確定状況ー商品と代金を交換した状態はTrue
    private boolean isConfirmed = false;

    //販売明細
    private List<SalesDetail> salesDetailList = new ArrayList<SalesDetail>();

    //商品IDごとの販売明細番号インデックス（商品の購入予定個数増減の際に商品IDから販売明細の番号を検索
    private Map<String, Integer> productIdIndex = new HashMap<>();

    /**
     * <p>販売情報生成</p>
     * - ビジネスロジック・ルール- 
     * 販売情報の一意の番号を採番する
     * getSalesNo()で取得可能
     */
    public Sales() throws SystemException {
        initSales();
    }

    /**
     * <p>販売情報生成</p>
     * - データ保管リポジトリ連携用 - 
     * リポジトリに保管されている販売情報をロードした際に利用
     * @param salesNo
     * @param salesDate
     * @param salesDateTime
     */
    public Sales(String salesNo, 
                 String salesDate, 
                 String salesDateTime) 
    throws SystemException {
        
        this.salesNo = salesNo;
        this.salesDate = salesDate;
        this.salesDateTime = salesDateTime;

    }

    /**
     * <p>販売明細追加</p>
     * - データ保管リポジトリ連携用 - 
     * リポジトリに保管されている販売情報をロードした際に販売明細を保持
     * @param salesDetail
     */
    public void addSalesDetail(SalesDetail salesDetail) 
    throws InputException {
        if (salesDetail == null)
            throw new InputException("空の商品明細は追加できません"); 
        
        this.salesDetailList.add(salesDetail);        
        this.productIdIndex.put(salesDetail.getProductId(), this.salesDetailList.size() - 1);

        reCalc();
    }

    /**
     * <p><販売明細一覧取得/p>
     * @return 販売明細一覧
     */
    public List<SalesDetail> getSalesDetailList() {
        return salesDetailList;
    }

    /**
     * <p><販売明細数取得/p>
     * @return 販売明細一覧数
     */
    public int countSalesDetail() {
        return salesDetailList.size();
    }

    /**
     * <p>販売明細取得</p>
     * @param detailNo 明細番号
     * @return 販売明細
     */
    public SalesDetail getSalesDetail(final int detailNo) 
    throws IndexOutOfBoundsException {
        return salesDetailList.get(detailNo);
    }

    /**
     * <p>販売番号再採番</p>
     * 販売伝票番号が重複する場合は再度日時から改めて採番する
     * データベースなどのリポジトリ保管時に番号が重複する場合に使用する
     * @throws SystemException
     */
    public void renumberingSalesNo() 
    throws SystemException {
        initSales();
    }

    /**
     * <p>購入商品数追加</p>
     *  - ドメイン・ビジネスロジック - 
     * @param product
     * @param increased
     * @return 追加・更新した販売明細情報のListインデックス番号
     * @throws InputException
     * @throws StatusException
     */
    public int addProduct(String productId, 
                           String productName,
                           int unitPrice,
                           final int increased) 
    throws InputException, StatusException, SystemException {

        if (this.isConfirmed)
            throw new StatusException("すでに確定している注文は変更できません");

        if (productId == null || productId.isEmpty() || 
            productName == null || productName.isEmpty() ||
            unitPrice <= 0)
            throw new InputException("商品ID、商品名なし、あるいは単価0円以下の商品は販売できません");

        //すでに販売明細に登録されている商品か検索
        Integer index = productIdIndex.get(productId);

        // 今回の注文で初めて選んだ商品は新しく明細を追加
        if (index == null) {
           
            String salesDetailNo = getNextDetailNo();
            salesDetailList.add(new SalesDetail(this.salesNo, 
                                                salesDetailNo,
                                                productId,
                                                productName,
                                                unitPrice,
                                                increased));

            index = Integer.valueOf(salesDetailList.size() - 1);
            productIdIndex.put(productId, index);
        }
        // すでに選んでいる商品の場合はすでにある明細の単価を更新し商品の点数を増加
        else {
            salesDetailList.get(index.intValue()).setUnitPrice(unitPrice);
            salesDetailList.get(index.intValue()).addQty(increased);
        }

        reCalc();

        return index.intValue();
    }

    /**
     *  <p>購入商品数削減</p>
     *  - ドメイン・ビジネスロジック - 
     * @param productId
     * @param decreased
     * @throws InputException
     * @throws StatusException
     */
    public void reduceProduct(final String productId, final int decreased) 
    throws InputException, StatusException {

        if (this.isConfirmed)
            throw new StatusException("すでに確定している注文は変更できません");

        final Integer index = productIdIndex.get(productId);

        if (index == null)
            throw new InputException("選ばれていない商品の点数を減らそうとしました "
                                   + "商品ID:" + productId + " 減少点数:" + decreased);

        // 選ばれている商品の点数から減らすと点数がゼロになる場合は明細から削除
        if (salesDetailList.get(index.intValue()).getQty() <= decreased) {
            salesDetailList.remove(index.intValue());
        } else {
            salesDetailList.get(index.intValue()).addQty(decreased);
        }

        reCalc();

    }

    /**
     *  <p>購入商品削除</p>
     *  - ドメイン・ビジネスロジック - 
     * @param productId
     * @throws InputException
     * @throws StatusException
     */
    public void removeProduct(final String productId) 
    throws InputException, StatusException {

        if (this.isConfirmed)
            throw new StatusException("すでに確定している注文は変更できません");

        final Integer index = productIdIndex.get(productId);

        if (index == null)
            throw new InputException("選ばれていない商品の点数を減らそうとしました。" 
                                   + "商品ID:" + productId);

        salesDetailList.remove(index.intValue());

        reCalc();

    }

    /**
     * <p>商品IDで明細を検索</p>
     * @param productId
     * @return
     */
    public SalesDetail getSalesDetailByProductId(String productId){
        Integer index =  productIdIndex.get(productId);
        if (index == null) return null;
        return salesDetailList.get(index);
    }

    /**
     * <p>販売を確定する</p>
     *  - ドメイン・ビジネスロジック - 
     */
    public void confirm() {
        this.isConfirmed = true;
    }

    /**
     * <p>販売の確定をキャンセルする</p>
     *  - ドメイン・ビジネスロジック - 
     */
    public void cancelConfirm() {
        this.isConfirmed = false;
    }

    /**
     * <p>販売完了状況kk</p> 
     * @return 販売完了している場合 True
     */
    public boolean isConfirmed() {
        return this.isConfirmed;
    }

    private void initSales() 
    throws SystemException {

        LocalDateTime now = LocalDateTime.now();
        this.salesDateTime = DateTimeUtil.toSimpleString(now);
        this.salesDate = DateTimeUtil.toSimpleString(now.toLocalDate());

        try {
            this.salesNo = SALES_NO_PREFIX + DateTimeUtil.toSimpleString(now);
        } catch (final SystemException e) {
            throw new SystemException("販売伝票NO採番エラー", e);
        }
    }   

    private String getNextDetailNo() 
    throws SystemException {

        if (salesDetailList.size() == 0) return this.salesNo + "-001";
        
        SalesDetail salesDetail = salesDetailList.get(salesDetailList.size() - 1);

        String lastDetailNo = salesDetail.getSalesDetailNo();
        
        int nextDetailNo = 0;
        try{
            nextDetailNo = Integer.parseInt(lastDetailNo.substring(
                            lastDetailNo.length()-3, lastDetailNo.length())) + 1;
        }
        catch(NumberFormatException nume){
            throw new SystemException("販売明細番号の採番でエラーが発生しました。", nume);
        }

        return this.salesNo + String.format("-%3d", nextDetailNo).replace(" ", "0");

    }

    /**
     * 明細点数・合計金額再計算処理
     */
    private void reCalc() {
        this.totalQty = 0;
        this.totalAmount = 0;

        for (SalesDetail  salesDetail : this.salesDetailList){
            this.totalQty += salesDetail.getQty();
            this.totalAmount += salesDetail.getAmount();
        }
    }
}