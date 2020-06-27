package jp.flowershop.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.flowershop.exception.InputException;
import jp.flowershop.exception.StatusException;
import jp.flowershop.exception.SystemException;

import jp.flowershop.domain.Sales;

/**
 * [FOR TRIAL]
 * ここにリポジトリのインタフェースをインポートする宣言をします。
 */

/**
 * <p>販売アプリケーション</p>
 * 販売のユースケースを実現するアプリケーション
 * SalesやProductのビジネスエンティティ（ルール・情報）
 * を利用して販売のユースケースを実現する
 */

/**
 * [FOR TRIAL]
 * ここにこのクラスがユースケースを担いデータ永続化のトランザクションもになう
 * ビジネスサービスのアプリケーションであることをアノテーションで宣言します。
 * 呼び出し元のControllerクラスでメンバ変数としてインタフェースが定義され
 * @Autowiredのアノテーションが記載されているとSpring Bootが検知します。
 * -----------------------------------------
 * @Service
 * @Transactional
 */
public class SalesApplicationImpl implements SalesApplication {

    /**
     * [FOR TRIAL]
     * ここにsalesテーブルから照会するリポジトリインタフェースを宣言します
     * またアノテーション@Autowiredを付与することでSpring Bootが
     * 検知し、Repositoryの初期化（データベース連携など）やRepositoryに
     * 基本的に求めらえる照会、更新などのメソッドが自動で実装されます。
     * ---------------------------------------
     * @Autowired
     * private SalesRepository repositorySales;
     */

    /**
     * <p>販売一覧を見る</p>
     * @return
     * @throws InputException
     * @throws SystemException
     */
    @Override
    public List<Sales> showSalesList() throws StatusException, SystemException {

        try{

            List<Sales> salesList = null;
            /**
             * [FOR TRIAL]
             * salesのリポジトリで自動生成sれたfindAllメソッドを使い
             * salesテーブルのデータをSalesクラスのList型でデータを取り込みます。
             */

            /**
             * [FOR TRIAL]
             * データ取得チェックリストが取得できない時のエラーを制御しましょう。
             * ・リストがnullの場合、"登録されている販売伝票はありません。"をExceptionの
             * 　メッセージにセットしStatusExceptionを生成しスローします。
             * ・リストが０件の場合、"登録されている販売伝票はありません。"をExceptionの
             * 　メッセージにセットしStatusExceptionを生成しスローします。
             * 
             *  例外クラスの使い分け
             *  InputException    何らかの入力が誤ってエラーが発生している可能性がある場合
             *  StatusException   現在の状態に起因するエラーでありユーザが状態を変えられる場合
             *  SystemException   システムに起因するエラーでありユーザーが回避策を取れない場合
             */

            return salesList;
        }
        /**
         * [FOR TRIAL]
         *  本プログラム中で生成したStatusExceptionはそのまま実行元に伝えるため記載します。
         *  ----------------------------------------
         *  catch(StatusException statuse){
         *      throw statuse;
         *  }
        */
        catch(Exception e){
            throw new SystemException("販売一覧の取得処理でエラーが発生しました。", e);
        }
    }
}
