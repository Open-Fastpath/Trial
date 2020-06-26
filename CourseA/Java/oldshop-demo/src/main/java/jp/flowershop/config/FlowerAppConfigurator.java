package jp.flowershop.config;

import jp.core.controller.WebControllerFactory;
import jp.core.exception.SystemException;
import jp.core.repository.RepositoryFactory;
import jp.core.config.IApplicationConfigurator;

import jp.flowershop.controller.*;
import jp.flowershop.repository.*;
import jp.flowershop.domain.*;

/**
 * <p>アプリケーション設定</p>
 * URIに対するControllerクラスを切替することでバックエンド処理の実装前の
 * ユーザインタフェース開発とスタブによるテストを可能とする。
 * また、ユースケースを実現するアプリケーション層がデータベースやファイルなど
 * 物理的なストレージを使う永続化処理からメモリ上のテスト用スタブに切替可能と
 * することでビジネスロジックを単独で自動テスト可能とする。
 * 
 */
public class FlowerAppConfigurator implements IApplicationConfigurator {
    
    public void config() throws SystemException{

        //URIのアクションに対し実行するControllerクラスを指定する
        WebControllerFactory.addController("start", AuthController.class);
        WebControllerFactory.addController("login", LoginController.class);
        WebControllerFactory.addController("menu", MenuController.class);
        WebControllerFactory.addController("saleslist", SalesListController.class);
        WebControllerFactory.addController("newsales", NewSalesController.class);
        WebControllerFactory.addController("cancelsales", CancelSalesController.class);
        WebControllerFactory.addController("salesdetaillist", SalesDetailListController.class);
        WebControllerFactory.addController("productselection", ProductSelectionController.class);
        WebControllerFactory.addController("selectproduct", SelectProductController.class);
        WebControllerFactory.addController("unselectproduct", UnselectProductController.class);
        WebControllerFactory.addController("about", AboutController.class);

        //domainのエンティティをデータベースなどに永続化するRepositoryクラスを指定する
        RepositoryFactory.addRepository(Sales.class.getName(), SalesRepositoryImpl.class);

        //データソース設定へのURIを指定
        RepositoryFactory.setDataContext("java:/comp/env/jdbc/FlowerDB");
    }

}