package jp.flowershop.repository;

import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

import jp.core.exception.InputException;
import jp.core.exception.SystemException;
import jp.core.repository.DataRepository;
import jp.flowershop.domain.Sales;
import jp.flowershop.domain.SalesDetail;

public class SalesRepositoryImpl extends DataRepository implements SalesRepository {

    @Override
    public List<Sales> findAllSalesList() throws InputException, SystemException {

        List<Sales> salesList = new ArrayList<>();

        try{
            String sql = "SELECT  * FROM sales ORDER BY sales_no DESC;";
            String[] param = null; 

            this.find(sql, param);

            if (this.rs == null){
                throw new InputException("登録されている販売伝票はありません。");
            }
            
            while(rs.next()){
                //取得データより販売情報を生成
                Sales sales = new Sales(rs.getString("sales_no"),
                                        rs.getString("sales_date"),
                                        rs.getString("sales_date_time"));
                sales.setTotalQty(rs.getInt("total_qty"));
                sales.setTotalAmount(rs.getInt("total_amount"));

                salesList.add(sales);
            }

            if (salesList.size() == 0){
                throw new InputException("登録されている販売伝票はありません。"); 
            }

            return salesList;

        }
        catch(SQLException sqle){
            throw new SystemException("販売情報・販売明細情報取得エラー", sqle);
        }
        catch(SystemException e){
            throw e;
        }
        finally{
            //ResultSet, PreparedStatus, Connectionのリソース解放
            releaseReference();
        }
    }

    @Override
    public Sales findBySalesNo(String salesNo) throws InputException, SystemException{

        try{
            String sql = "SELECT * FROM sales WHERE sales_no=?";
            String[] param = { salesNo };
            this.find(sql, param);

            if (rs == null || rs.next()==false){
                releaseReference();
                throw new InputException("指定された販売伝票番号に該当する販売はありませんでした"
                                       + " 販売伝票番号:" + salesNo);           
            }
            
            //取得データより販売情報を生成
            Sales sales = new Sales(salesNo,
                                    rs.getString("sales_date"),
                                    rs.getString("sales_date_time"));
            releaseReference();

            sql = "SELECT * FROM sales_detail WHERE sales_no=?";
            this.find(sql, param);

            while(rs.next()) {
                //取得データより販売明細情報を生成
                SalesDetail detail
                    = new SalesDetail(rs.getString("sales_no"),
                                    rs.getString("sales_detail_no"),
                                    rs.getString("product_id"),
                                    rs.getString("product_name"),
                                    rs.getInt("unit_price"),
                                    rs.getInt("qty"));

                sales.addSalesDetail(detail);
            }
            releaseReference();

            return sales;
        }
        catch(SQLException sqle){
            throw new SystemException("販売情報・販売明細情報取得エラー", sqle);
        }
        catch(SystemException e){
            throw e;
        }
        finally{
            //ResultSet, PreparedStatus, Connectionのリソース解放
            releaseReference();
        }
    }

    @Override
    public void registor(Sales sales) throws SystemException {

        try{
            String sql = "INSERT INTO flower_db.sales "
                       + "(sales_no, sales_date, sales_date_time, "
                       + " total_qty, total_amount) "
                       + "VALUES(?, ?, ?, ?, ?);";

            Object[] param = { sales.getSalesNo(),
                               sales.getSalesDate(),
                               sales.getSalesDateTime(),
                               sales.getTotalQty(),
                               sales.getTotalAmount()
                             };
            this.execute(sql, param);
        }
        catch(SystemException e){
            throw new SystemException("販売情報・販売情報登録エラー", e);
        }
        finally{
            releaseExecutable();
        }
    }

    @Override
    public void registor(SalesDetail salesDetail) throws SystemException {

        try{
            String sql = "INSERT INTO flower_db.sales_detail "
                       + "(sales_detail_no, sales_no, product_id, product_name, "
                       + " unit_price, qty, amount) "
                       + "VALUES(?, ?, ?, ?, ?, ?, ?);";

            Object[] param = { salesDetail.getSalesDetaillNo(),
                               salesDetail.getSalesNo(),
                               salesDetail.getProductId(),
                               salesDetail.getProductName(),
                               salesDetail.getUnitPrice(),
                               salesDetail.getQty(),
                               salesDetail.getAmount()
                             };
            this.execute(sql, param);
        }
        catch(SystemException e){
            throw new SystemException("販売情報・販売明細情報登録エラー", e);
        }
        finally{
            releaseExecutable();
        }
    }

    @Override
    public void update(Sales sales) throws SystemException {
        try{
            String sql = "UPDATE flower_db.sales "
                       + "SET sales_date=?, sales_date_time=?, "
                       + " total_qty=?, total_amount=? "
                       + "WHERE sales_no=?";

            Object[] param = { sales.getSalesDate(),
                               sales.getSalesDateTime(),
                               sales.getTotalQty(),
                               sales.getTotalAmount(),
                               sales.getSalesNo() 
                             };
            this.execute(sql, param);
        }
        catch(SystemException e){
            throw new SystemException("販売情報・販売情報更新エラー", e);
        }
        finally{
            releaseExecutable();
        }
    }

    @Override
    public void update(SalesDetail salesDetail) throws SystemException {

        try{
            String sql = "UPDATE flower_db.sales_detail "
                       + "SET product_id=?, product_name=?, "
                       + " unit_price=?, qty=?, amount=? "
                       + "WHERE sales_detail_no=?;";

            Object[] param = { salesDetail.getProductId(),
                               salesDetail.getProductName(),
                               salesDetail.getUnitPrice(),
                               salesDetail.getQty(),
                               salesDetail.getAmount(),
                               salesDetail.getSalesDetaillNo() 
                             };
            this.execute(sql, param);
        }
        catch(SystemException e){
            throw new SystemException("販売情報・販売明細情報更新エラー", e);
        }
        finally{
            releaseExecutable();
        }
    }

    @Override
    public void remove(Sales sales) throws SystemException{

        try{
            Object[] param = { sales.getSalesNo() };

            String sql = "DELETE FROM flower_db.sales_detail WHERE sales_no=?";

            this.execute(sql, param);

            sql = "DELETE FROM flower_db.sales WHERE sales_no=?";

            this.execute(sql, param);
        }
        catch(SystemException e){
            throw new SystemException("販売情報・販売情報削除エラー", e);
        }
        finally{
            releaseExecutable();
        }
    }

    @Override
    public void remove(SalesDetail salesDetail) throws SystemException{

        try{
            Object[] param = { salesDetail.getSalesDetaillNo() };

            String sql = "DELETE FROM flower_db.sales_detail WHERE sales_detail_no=?";

            this.execute(sql, param);
        }
        catch(SystemException e){
            throw new SystemException("販売情報・販売情細削除エラー", e);
        }
        finally{
            releaseExecutable();
        }
    }
}