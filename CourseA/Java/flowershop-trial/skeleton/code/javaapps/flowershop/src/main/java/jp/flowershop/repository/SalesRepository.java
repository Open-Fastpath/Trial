package jp.flowershop.repository;

/**
 * [FOR TRIAL]
 * データ処理を担うリポジトリインタフェースを実装してください。
 * 
 * 従来はこのインタフェースを実装するクラスは開発しなければなりませんでしたが
 * （以前のPure Javaで実装しているoldshopのデモアプリケーション参照）。
 * Spring BootやSpring Data JPAのしくみで複雑なXMLファイルなどもなく
 * 自動で実装されます。
 * 
 * （１）下記3つのオプジェクトを参照のためにimport宣言します。
 *      -------------------------
 *      jorg.springframework.data.jpa.repository.JpaRepository
 *      org.springframework.stereotype.Repository
 *      jp.flowershop.domain.Sales
 * 
 * （２）インタフェース宣言の上に@Repositoryアノテーションを追記します。
 * 　　　これを利用するSalesApplicationクラスでこのインタフェースが
 * 　　　宣言され、@Autowiredとなっていると初期化が全部実施されます。
 * 　　　その時このインタフェースがリポジトリの自動実装の対象であることを
 * 　　　Spring Bootに知らせます。
 * 
 * （３）JpaRepositoryインタフェースを継承します。その時リポジトリが扱う
 * 　　　型とプライマリキーとする項目のJava上のデータ型を指定します。
 * 　　　salesテーブルのsales_noがプライマリキーであり文字列型であるため
 * 　　　String型であることを示します。
 *      -------------------------
 *      extends JpaRepository<Sales, String>
 */

public interface SalesRepository {
    //ここはトライアルでは何も記載する必要はありません。
}