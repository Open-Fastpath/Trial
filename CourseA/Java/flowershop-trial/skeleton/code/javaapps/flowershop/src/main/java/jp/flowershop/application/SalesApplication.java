package jp.flowershop.application;

import java.util.List;

import jp.flowershop.exception.InputException;
import jp.flowershop.exception.StatusException;
import jp.flowershop.exception.SystemException;

import jp.flowershop.domain.Sales;

public interface SalesApplication {
    
    public List<Sales> showSalesList() throws StatusException, SystemException;
    
}