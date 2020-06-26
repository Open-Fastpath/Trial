package jp.flowershop.repository;

import java.util.List;

import jp.core.exception.InputException;
import jp.core.exception.SystemException;

import jp.flowershop.domain.Sales;
import jp.flowershop.domain.SalesDetail;

public interface SalesRepository {

    public Sales findBySalesNo(String salesNo) throws InputException, SystemException;
    public List<Sales> findAllSalesList() throws InputException, SystemException;
    public void registor(Sales sales) throws SystemException;
    public void registor(SalesDetail salesDetail) throws SystemException;
    public void update(Sales sales) throws SystemException;
    public void update(SalesDetail salesDetail) throws SystemException;
    public void remove(Sales sales) throws SystemException;
    public void remove(SalesDetail salesDetail) throws SystemException;

}