package jp.flowershop.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.core.application.UseCaseApplication;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;
import jp.core.repository.RepositoryFactory;

import jp.flowershop.domain.Sales;
import jp.flowershop.domain.SalesDetail;
import jp.flowershop.domain.Product;
import jp.flowershop.repository.SalesRepository;

/**
 * <p>販売アプリケーション</p>
 * 販売のユースケースを実現するアプリケーション
 * SalesやProductのビジネスエンティティ（ルール・情報）
 * を利用して販売のユースケースを実現する
 */
public class SalesApplicationImpl extends UseCaseApplication{

    private List<Product> salesProductList = null;      //購入可能商品一覧
    private Map<String, Product> productIdMap = null;   //商品コードをキーとしたマップ
    private jp.flowershop.domain.Sales sales = null;        //販売エンティティ（ルールと情報）
    private SalesRepository repository =null;               //販売情報を永続化しているリポジトリ管理

    /**
     * <p>販売アプリケーション初期化</p>
     * ・販売可能商品の一覧を準備する
     * ・販売を保管するリポジトリの用意と販売情報を初期化する
     */
    public SalesApplicationImpl()
    throws SystemException{
        
        //最初の花屋さんは３つの商品のみを扱う
        salesProductList = new ArrayList<>();
        productIdMap = new HashMap<>();

        try{
            salesProductList.add(new Product("FW00000010", "ばら", 500, "20200601"));
            salesProductList.add(new Product("FW00000020", "チューリップ", 300, "20200601"));
            salesProductList.add(new Product("FW00000030", "コスモス", 400, "20200601"));

            for (Product Product : salesProductList) {
                productIdMap.put(Product.getProductId(), Product);
            }
        }
        catch(InputException e){
            throw new SystemException("購入可能な商品リストの初期化に失敗しました", e);
        }

        //Salesのデータを保管しているデータベースとの処理を担うリポジトリ管理クラスを取得
        repository  = (SalesRepository) RepositoryFactory.getRepository(Sales.class.getName());
    }

    /**
     * <p>販売一覧を見る</p>
     * @return
     * @exception InputException
     * @exception SystemException
     */
    public List<Sales> showSalesList() 
    throws InputException, SystemException{

        return repository.findAllSalesList();

    }

    /**
     * <p>販売の明細を見る</p>
     */
    public Sales showSales(String salesNo)
    throws InputException, SystemException{

        if (salesNo == null){
            throw new InputException("販売伝票番号が指定されておりません。");
        }

        return repository.findBySalesNo(salesNo);
    }

    /**
     * <p>新規販売</p>
     * 新たに販売を開始する
     */
    public Sales newSales() throws SystemException{

        this.sales = new Sales();
        repository.registor(sales);

        return this.sales;

    }

    /**
     * <p>購入できる商品のリストを見る</p>
    * @return 購入可能な商品の一覧
     */
    public List<Product> showSalesProducts(){

        return this.salesProductList;

    }
    
    /**
     * <p>商品を選ぶ</p>
     * 単価がマスタと異なる場合は指定された単価で更新する
     * すでに標準単価で商品が選択されている分の単価も更新される
     * @param productId
     * @param unitPrice
     * @param qty
     * @throws InputException
     * @throws StatusException
     */
    public void selectProduct(String salesNo, String productId, int unitPrice, int qty)
    throws InputException, StatusException, SystemException{
       
        if (salesNo == null){
            throw new InputException("販売伝票番号が指定されておりません。");
        }

        Sales sales = repository.findBySalesNo(salesNo);
        if (sales == null){
            throw new InputException("販売伝票番号が存在しません。");
        }

        if (productId == null){
            throw new InputException("商品コードを選択し数量を入力してください。");
        }

        Product selected = this.productIdMap.get(productId);
        if (selected == null) {
            throw new InputException("選択した商品コードは販売中ではありません "
                                   + "商品コード:" + productId);
        }

        try{
            int originalDetailCount = sales.countSalesDetail();
            int editDetailNo = sales.addProduct(selected.getProductId(), 
                                                selected.getProductName(),
                                                unitPrice,
                                                qty);

            if (originalDetailCount + 1 == sales.countSalesDetail()){
                repository.registor(sales.getSalesDetail(editDetailNo));
            }
            else{
                repository.update(sales.getSalesDetail(editDetailNo));
            }

            repository.update(sales);
        }
        catch(InputException inpute){
            throw inpute;
        }
        catch(StatusException e){
            throw e;
        }
    }

     /**
     * <p>購入予定の全ての商品・数量・金額の明細を見る</p>
     * @return　購入予定の全ての明細
     */
    public List<SalesDetail> showAllDetailList(){

        return this.sales.getSalesDetailList();

    }

    /**
     * <p>選んだ商品を全て購入対象から外す</p>
     */
    public Sales removeSalesDetail(String salesNo, String productId)   
    throws InputException, StatusException, SystemException {
        
        try{
            Product selected = this.productIdMap.get(productId);
            if (selected == null) 
                throw new InputException("選択した商品コードは販売中の商品ではありません "
                                       + "商品コード:" + productId);                                  

            Sales sales = repository.findBySalesNo(salesNo);
            if (sales == null) {
                throw new InputException("販売伝票番号が存在しません。" + salesNo);
            }

            SalesDetail salesDetail = sales.getSalesDetailByProductId(productId);
            repository.remove(salesDetail);

            sales.removeProduct(productId);

            repository.update(sales);

            return sales;
        }
        catch(InputException inpute){
            throw inpute;
        }
        catch(StatusException e){
            throw e;
        }
    }

    /**
     * <p>合計金額を確認する</p>
     * @return 販売予定の合計金額
     */
    public int showTotalAmount() {
        return this.sales.getTotalAmount();
    }

    /**
     * <p>販売する</p>
     * お金を受取りお釣りを計算し商品をお渡しする
     * 販売は確定し記録される（以後キャンセルしないかぎり変更不可）
     * @param paymentAmount お支払額
     * @return お釣りの金額
     * @exception InputException お支払額が足りない場合
     */
    public int sell(int paymentAmount)
    throws InputException, SystemException{

        int changeAmount = paymentAmount - sales.getTotalAmount();

        if (changeAmount < 0) 
            throw new InputException("お支払額が足りません "
                                   + "お支払額:" + paymentAmount
                                   + "購入額:" + this.sales.getTotalAmount());

        //以後販売を変更できないように確定とする       
        this.sales.confirm();

        //リポジトリに販売情報を記録
        repository.registor(this.sales);
        
        //お釣りを返金
        return changeAmount;
    }

    /**
     * <p>商品の返品を受ける</p>
     * 商品を受取り検品しお支払額を返金する
     * 販売情報の記録は抹消される。
     * ※将来は返品の記録をするようにしていく
     * @param salesNo 販売伝票番号
     * @return 返金する額
     * @exception InputException 指定された販売伝票番号に該当する記録がない場合
     */
    public int cancelSales(String salesNo)
    throws InputException, SystemException{

        //データを保管しているリポジトリからSalesエンティティを照会
        this.sales = repository.findBySalesNo(salesNo);

        if (this.sales == null) 
            throw new InputException("返品に指定された販売伝票番号は存在しません");

        //返金額を算定
        int repaymentAmount = this.sales.getTotalAmount();    
        
        //販売情報を抹消
        repository.remove(this.sales);

        //返金する
        return repaymentAmount;
    }
}