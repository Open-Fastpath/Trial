package jp.flowershop.controller;

 /**
  * [FOR TRIAL]
  * （１）Spring Bootで自動実装されるようにオブジェクトをインポートします。
  *      直接import文を記載しても、宣言がないオブジェクト宣言をマウスでフォーカスし
  *      クイックフィックスからimport オブジェクト名で自動インポートしてみても構いません。
  *      --------------------------------------------------------
  *      org.springframework.beans.factory.annotation.Autowired;
  *      org.springframework.boot.autoconfigure.EnableAutoConfiguration;
  *      org.springframework.stereotype.Controller;
  *      org.springframework.web.bind.annotation.GetMapping; 
  *
  * （２）クラス宣言の前にSpring Bootが本クラスをControllerと認識し自動設定するための
  *      アノテーションを追加します。
  *      --------------------------------------------------------
  *      @@Controller
  *      @EnableAutoConfiguratio
  *
  * （３）利用するアプリケーションSalesApplicationをインタフェースをprivateメンバで宣言し
  *      Spring BootがSalesApplicationで実装されるServiceとして必要な初期化を自動で実装
  *      してくれるようにアノテーションを記載します。
  *      --------------------------------------------------------
  *      @Autowired
  *      private SalesApplication app;
  *     
  * （４）このクラスのserviceメソッドが、ブラウザからhttp://localhost:8080/saleslistで
  *      アクセスした時に呼び出しされるよう
  *      Spring Bootが自動的にマッピングしてくれるようにserviceメソッドの上にアノテーション
  *      アノテーションを記載します。
  *      ここではURIをブラウザで入力して実行する際に呼び出されるHTTP GETメソッドで
  *      URIのパス”/saleslist”が実行された時に呼び出されるように指定します。
  *      --------------------------------------------------------
  *      @GetMapping("/saleslist")
  *
  */
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.flowershop.exception.InputException;
import jp.flowershop.exception.StatusException;
import jp.flowershop.exception.SystemException;
import jp.flowershop.application.SalesApplication;
import jp.flowershop.domain.Sales;

public class SalesListController {

    protected String service(Model model)
    throws InputException, StatusException, SystemException {

        try{
            List<Sales> salesList = null;

            /**
             * [FOR TRIAL]
             * SalesApplicationインタフェースのshowSalesListメソッドを
             * 実行してSalesのListを取得します。
             */

            //Thymeleafが参照するView Modelとして取得した一覧をセット
            model.addAttribute("saleslist", salesList);
        }
        catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
        }

        //表示するViewはSpring Bootが指定した名称に”.html”の拡張子をつけ
        //src/main/resources/templatesフォルダを検索します。
        return "saleslistview";        
    }
}