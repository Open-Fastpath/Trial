# OpenFastPathトライアルトレーニングWindows直インストール版
-ー　 version 2020.06.26　ー

## 前提

* 本資料は「OpenFastPathトライアル環境構築Windows直インストール版」にてWindows Home上に直接ミドルウェアをインストールする必要があるPCに適用する手順である。

## ミッション「販売を一覧で見えるようにする」

### 背景

花屋さんのお得意様にかつてプログラミングができる方がおられた。
当時は日々の売上を余っているパソコンで記録できないかと考え、途中まで作っていただいたデモンストレーションプログラムがある。

その方は急に忙しくなってしまい、こちらもボランティアでお願いしていたため、無理に作って欲しいとは言えなかった。

花屋さんからの依頼は、在庫削減策を本格的に開始する前にお店の売上が見えるとありがたいということである。

一から自由に作っていただいても構わないし、この作りかけのアプリケーションを参考にしてもらっても良いとのこと。

### トライアルの対象

* トップページ画面→ログイン画面→メニュー画面→販売一覧画面までを完成させること
*  「Open Fast Pathトレーニング環境構築手順」構築したVS CodeのSpring Boot環境と、Dockerコンテナで構築したMySQL環境に接続し実装すること
* Spring Boot＋Springファミリーのどの機能を利用しても良い。サンプルはThymeleaf、Spring Web、Spring Data JPAを利用している。（React / Vueはモバイル化のトレーニングで実施予定。）
* Web UIは将来のモバイル化の睨みレスポンシブルデザインが可能なBootstrap4と初心者でも使いやすい軽量なCSSフレームワークButter Cakeのどちらも利用可能。
* トップページ画面からメニュー画面までの機能は上記技術で簡易に実装したものが提供し、販売一覧もHTMLだけは用意している。
* 後述の仕様に基づきデータベースから販売情報を取得し表示するまでを構築することがミッションとなる。
* トライアルの所要時間は、集中して２時間から３時間を想定している.。

### 販売一覧の仕様

* 下記画面遷移までは後述の「トライアルトレーニング用のソースコード」のダウンロードにより提供される
	* ブラウザで`http://localhost:8090/`で「トップページ」画面表示。（Tomcatなどとポートが衝突しないようにSpring Bootは8090ポートで起動するようにしている。変更する際にはVS Code上から`src\main\java\resources\application.properties`を開き、server.port=8090）を変更する）
	* トップページ画面にて「業務をはじめるにはログインしてください」をクリック。→ログイン画面に遷移。
	*  ログイン画面にてユーザーID「tencyo」パスワード「ohanayasan」を入力し「Login」をクリック。→メニュー画面に遷移。
* 提供するメニュー画面より、下記３カ所から販売一覧画面に遷移する。
	* ナビゲーションバーにある「販売業務」リンク
	* カードタイルの「販売業務」リンク
	* カードタイルの「販売業務」にある「こちらから開始」リンク
* 販売一覧へのURLは`http://localhost:8090/saleslist`とする。
* 販売一覧はMySQLに格納されている「sales」テーブルの情報を全て表示する。販売数が少ないため、検索条件や絞り込みをせず全件表示する。
* 販売一覧の一覧表の表示項目は下記順に左から並ぶものとしデータベース定義のままとする。
	* 販売日　　　　salesテーブルのsales_dateの項目の値を表示
	* 販売伝票番号　salesテーブルのsales_noの項目の値を表示
	* 販売日時　　　salesテーブルのsales_date_timeの項目の値を表示
	* 販売点数　　　salesテーブルのtotal_qtyの項目の値を表示
	* 合計金額　　　salesテーブルのtotal_amountの項目の値を表示

### 選べるトライアルのアプローチ

本トライアルは受講者の力量に応じて３種の選択肢が用意され選ぶことができる。
	
1. 前述仕様に基づき自ら実装方法を考え実装する。
2. 前のプログラマが実装したデモアプリケーションのプログラムを参考にして実装する。（後述：「デモアプリケーションのプログラムを動かす」参照）
3. 2に加え、販売一覧のスケルトンプログラムのコメントに従い穴埋め数rことで実装する。（後述：「デモアプリケーションのプログラムを動かす」と「トライアルプログラムのダウンロード」内の「スケルトンプログラムを利用する」を参照）

いずれのパターンでも、メニューとドメインモデルの一部の機能までは実装済みプログラムを提供する。後述の「トライアルプログラムのダウンロード」は全員実施する。

- - - -


## トライアルプログラムの配置（全員実施）

### トライアル用のソースコードの配置

* Power Shellを起動し、Githubよりトライアル用のキットをダウンロードし開発用のフォルダに配置。
```
cd c:\workspace
git clone https://github.com/Open-Fastpath/Trial.git
cp -Recurse -PassThru
.\Trial\CourseA\Java\flowershop-trial .
```

### MySQLにデータベーステーブル設定

1. データベース設定ファイル格納フォルダを開く

* VS Codeを起動し、「アクティブバー」の「エクスプローラ」を選び「フォルダーを開く」で`C:\workspace\flowershop-trial\code\javaapps\flowershop\repository\mysql\ddl`を開く
	
2. 販売管理テーブルの生成

* 「アクティブバー」から「エクスプローラ」を選択し、ファイルより`create_tables.sql`を選択。`SHIFT+CTRL+P`で「コマンドパレット」を開き、「SQLTools:Connection Run This File」を選び、接続先DBに`vs_con_flower_db `を選択して実行。
* Resultが待ち時間になっている場合でも処理が完了しているケースがあるため、「アクティブバー」から「SQL Tools」を選択し、「CONNECTIONS」→「vs_con_flower_db」→「flower_db」→「Tables」を開き`sales`と`sales_detail`の２つのテーブルが作成されていることを確認。
* 	VS CodeのSQLToolsでSQLが実行できない場合は、「スタート」の横の検索窓で「MySQL」と入力し「MySQLx.x Comand Line Client」を起動。インストール時に設定したRoot passwordでログイン。SQLファイルを実行する。
```
mysql> use flower_db
Database changed
mysql> source C:\workspace\flowershop-trial\code\javaapps\flowershop\repository\mysql\ddl\create_tables.sql
```

3. 販売管理テーブルにテストデータを登録
	
* 同様の手順で、ファイルより`insert_sales.sql`を選択しSQLを実行。ファイルを開いた上段に「Run on active connection」と出ていれば左の実行ボタンをクリックして実行しても良い。
* Resultが待ち時間になっている場合でも処理が完了しているケースがあるため、「アクティブバー」から「SQL Tools」を選択し、「CONNECTIONS」→「vs_con_flower_db」→「flower_db」→「Tables」→「sales」「sales_detail」と開き、「sales」「sales_detail」それぞれの右側にある「Show Table Records」をクリック。レコードが登録されていることを確認する。
* 	VS CodeのSQLToolsでSQLが実行できない場合は、「スタート」の横の検索窓で「MySQL」と入力し「MySQLx.x Comand Line Client」を起動。インストール時に設定したRoot passwordでログイン。SQLファイルを実行する。（GitよりダウンロードするファイルはUtf8で保存され、データに日本語を含むためそのまま読み込みするとエラーとなる。`C:\workspace\flowershop-trial\code\javaapps\flowershop\repository\mysql\ddl\insert_sales.sql`をメモ帳などで開き、名前をつけて保存で文字コードを`ANSI`にし`insert_sales_CP932.sql`などで保存して実施する。）
```
mysql> use flower_db
Database changed
mysql> source C:\workspace\flowershop-trial\code\javaapps\flowershop\repository\mysql\ddl\insert_sales_CP932.sql
```

### 実装・ビルド・稼働確認

1. ソースコードフォルダのオープン・実装とデバッグ
	
* VS Codeの「アクティブバー」から「エクスプローラ」を選び「フォルダを開く」でVS Codeでソースコードのルートディレクトリ`C:\workspace\flowershop-trial\code\javaapps\flowershop`を指定し開く。 
* 「エクスプローラ」で`.vscode`→`setting.json`を開き`java.home`を`"java.home": "C:\\workspace\\jdk",`に修正して保存。
*  VS Codeのステータスバーの右下で回転マークが動いていたらマークをクリックし、ターミナル上で依存関係があるパッケージのダウンロードとビルドが全てDoneになるまで待つ。（※途中で止まるようであれば下記継続して実施して良い。）
* 今回のテーマ「販売を一覧で見えるようにする」を実装し保存する。
* VS Codeの「表示」→「問題」にて VS Codeの自動コンパイルがなされて検知できる問題がリアルタイムに表示される。
* 表示箇所をクリックすると問題箇所にジャンプするため対象箇所を修正する。
* 問題によっては問題箇所にマウスオーバーするとクイックフィックスのリンクが表示されることがある。クイックフィックスの不具合修正が妥当かは人による判断が必要なものもあるため確認せずに反映しないように注意すること。
* Windows環境の場合、導入されるmysqlのドライバが5.x系の場合、timezoneが認識されない不具合があるため、`src¥main¥resources¥applications.properties`に設定しているデータソースの接続定義に明示的にタイムゾーンの指定を追加する。
```
spring.datasource.url=jdbc:mysql://localhost:3306/flower_db?serverTimezone=JST
```
	
```
ーーVS Codeの自動検知の問題ーー
開発対象のプログラム自体が参照可能でありプログラムも動作するが
「シンボルを見つけられません」「パッケージ xxx は存在しません」となる現象がある。VS Codeの問題で警告表示されるがこれは無視をして良い。
それ以外の問題や警告はすべて修正する。
```

2. 実装後のビルドとデバッグ
	
* 「Ctrl/Command」＋「Shift」+Pでコマンドパレットを開き`task`と入力。タスク：ビルドタスクの実行を選択し、ターミナルでGradleのbuildタスクを実行する。`BUILD SUCCESSFUL`が表示されたら次のSpring Bootを実行する`BUILD FAILED`が表示されたらログから原因を解析し修正する。
	
```
ーVS Codeのタスクの設定ー
ビルドタスクは`.vscode¥tasks.json`でカスタマイズできる。上記ビルドタスクの実装は`tasks.json`で`isDefault": true`がされているタスクがデフォルトで実行される。個別に指定して実行する場合はVS Codeのメニューから「ターミナル」→「タスクの実行」をすると`tasks.json`の`”label”`が表示され、選択すると`"command"`で定義されているコマンドが実行される。
```
	
3. .稼働確認

* VS Codeのメニューで「実行」→「デバッグの開始」を選択。
* 起動される「デバッグコンソール」（自動起動されない場合はVS Codeのメニューで「表示」→「デバッグコンソール」を選択し表示）でSpring Bootが起動されることを確認。
* ブラウザにて`http://localhost:8090/`でトップページを表示し、ログイン画面（ユーザID：tencyo  パスワード：ohanayasan）→メニュー画面→販売一覧画面に遷移しデータが表示されることを確認。 

- - - -


## スケルトンプログラムを利用する（希望者のみ）

1. スケルトンプログラムの反映
スケルトンプログラムは、「トライアル用のソースコードのダウンロード」でダウンロードしたファイルに含まれている。
	
* Power Shellを起動し、下記手順でスケルトンプログラムも作業ディレクトリに反映する。
```
cd C:\workspace\flowershop-trial\
cp -Recurse -Force .\skeleton\code .
```

2. スケルトンプログラムでの実装

* 実装対象オブジェクト
※VS Code上は`src\main\java\jp\flowershop`配下のパスから記載
	* application/SalesApplication.java
	* contoroller/SalesListController.java
	* domain/Sales.java   
	* repository/SalesRepository.java

	* プログラム上の指示例
```
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
・・・
public class SalesListController {

・・・
            /**
             * [FOR TRIAL]
             * SalesApplicationインタフェースのshowSalesListメソッドを
             * 実行してSalesのListを取得します。
             */

            //Thymeleafが参照するView Modelとして取得した一覧をセット
            model.addAttribute("saleslist", salesList);	
```

* 販売一覧の関連リソースファイル
※VS Code上はsrc_main_resources配下のパスから記載	
	* static/css　CSSテンプレート、JavaScriptフレームワーク
	* images       HTMLより利用するイメージファイル
	* templates/saleslistview.html     販売一覧画面のHTMLテンプレート
	* templates/navi.html        共通のナビゲーションメニューのHTMLテンプレート
	
* 	実装手順は上記プログラム上の実装箇所に`[FOR TRIAL]`のキーワードで詳細な実装方法を記載しているため記述の通りに実装する。
* 依存関係などがエラーとなる場合は、`Ctrl+Shift+p`でコマンドパレットを起動し、検索窓に「clean」と入力。「Java: Clean the Java language server workspace」を実行すると再度ビルドが走る。右下の回転マークをkクリックすると「ターミナル」にビルドの状況が見える様になる。	

- - - -

## デモアプリケーションのプログラムを動かす

あなたはWebアプリケーションを作るのが初めてであったため、まずはそのプログラムをいただいて、自分のパソコンで動かしてみることにした。

いただいたプログラムには簡単な環境構築手順が書いてあった。これを参考にまずは動かしてみることにした。

### このプログラムを動かす手順

* Tomcatのインストール 
* デモプログラムのビルド
* Tomcatへのデプロイ
* デモアプリケーションの稼働確認
* デモアプリケーションのソースコード確認

### Tomcatインストール

* `C:¥workspace¥tomcat`フォルダを作成。
* [Apache Tomcat® - Welcome!](http://tomcat.apache.org/)より左のメニューから「ダウンロード」「Tomcat9」を選択。`which version`リンクをクリック。
* 32-bit/64-bit Windows Service Installerをダウンロード。
* インストーラを起動し、下記のみ設定を変更しインストール。
	* 「Tomcat Administrator Login」のユーザIDとパスワードに「tomcatmanager」を入力
	* シャットダウンポートに`8005`を設定
	* JREのインストールディレクトリは`C:¥workspace¥jdk`を指定。
	* インストール先は`C:¥workspace¥tomcat`に変更
	* 「Run Apache Tomcat」と「Show Readme」はチェックを外して完了。
* 「スタート」の横の検索窓で「システム環境変数」と入力し「システム環境変数の編集」をクリック。「システムのプロパティ」で「環境変数」をクリック。「システム環境変数」の「新規」をクリック。現れたダイアログで「変数名」に`CATALINA_HOME`「変数値」は「ディレクトリの参照」をクリックし`C:¥workspace¥tomcat`を選択。
* Power Shellを起動し、下記コマンドで環境変数が設定されていることを確認。(※コマンドラインの場合は%環境変数%だがPower Shellの場合は$env:環境変数。)
```
echo $env:JAVA_HOME	
echo $env:CATALINA_HOME
```

* TomcatのログのデフォルトはUTF-8であるが、WindowsのターミナルはCP932とSJ ISであるためログの設定をSJISにする。
`C:¥workspace¥tomcat¥conf¥logging.properties`設定を修正
```
#java.util.logging.ConsoleHandler.encoding = UTF-8
java.util.logging.ConsoleHandler.encoding = SJIS
```

*  Tomcatのサービスをスタート（※プロンプトが文字化けする場合は左上のアイコンをクリックし「プロパティ」→「フォント」で「BIZ UIゴシック」を選択。）
```
cd $env:CATALINA_HOME\bin
.\startup.bat
```

* Tomcatのターミナルが起動され、ファイヤウォールを許可するか確認するダイアログが表示されるため、「許可する」をクリック。
* ブラウザで`http://localhost:8080/`を入力しTomcatのスタートページが表示されることを確認。
* ブラウザで`http://localhost:8080/manager`を入力し,、認証でインストール時に指定した`tomcatmanager`をユーザIDとパスワードに指定。Tomcatの管理ページが表示されることを確認。
* Power ShellのターミナルでTomcatのサービスを停止
```
cd $env:CATALINA_HOME\bin
.\shutdown.bat
```

### デモプログラムのビルド

1. デモプログラムの配置

* Power Shellを起動し、Githubよりダウンロードしたデモプログラムのソースコードを開発用のフォルダに配置。
```
cd c:\workspace
cp -Recurse -PassThru .\Trial\CourseA\Java\oldshop-demo .
```

2. デモアプリケーションをGradleでビルド

* VS Codeを起動しメニューの「ファイル」→「フォルダを開く」で`C:\workspace\oldshop-demo`フォルダを開く。
*  Windows環境の場合、導入されるmysqlのドライバが5.x系の場合、timezoneが認識されない不具合があるため、`webapp\META-INF\context.xml`に設定しているデータソースの接続定義に明示的にタイムゾーンの指定を追加する。
```
    url="jdbc:mysql://localhost:3306/flower_db?serverTimezone=JST"/>
```

* 「アクティブバー」より「エクスプローラ」を選び、左のファイル構成から`build.gradle`を選択。`dependencies`のTomcatフォルダのパスを書き換える
```
compileOnly files('C:\\workspace\\tomcat\\lib\\servlet-api.jar', 'C:\\workspace\\tomcat\\lib\\jsp-api.jar' )	
```
* `Ctrl+Shift+P`でコマンドパレットを開き、「タスク：ビルドタスクの実行」を選択し、WARファイル（Web application ARchive）を生成する。（事前に配布されているbuild.gradleの設定に従いコンパイルからパッケージングまで実行される）
* PowerShellでビルドツールgradleを用い、WARファイル（Web application ARchive）を生成することも可能。
```
cd .\oldshop-demo\
gradle build
gradle war	
```

```
ーWAR（Web application ARchive）ファイルー
WARファイルとは、Webアプリケーションを動作させるのに必要な、HTMLファイル、CSSファイル、JavaScriptファイル、javaなどのサーバプログラム、実行に必要なライブラリなどの一連の構成を全てパッケージし、ディレクトリ構成ごと圧縮してデプロイーアプリケーションの配布・リリースを容易に実施するための仕組みである。

TomcatなどのWebアプリケーションサーバでは、WARファイルをWeb アプリケーションを配置するフォルダに置くと、自動的に展開して実行可能となる。
通常はアプリケーションのコンテキストルート（Webからアクセスした場合にhttp://ドメイン名/でアクセスできるルートディレクトリ）の直下にWARファイルを配置すると、http://ドメイン名/(WARファイル名の.warを除いた名称)でアクセスすることができる。
```

* 「gradle」の実行結果として`BUILD SUCCESSFUL`が表示されたら、PowerShell、またはVS Codeでメニューから「表示」→「ターミナル」を選択し、`c:\workspace\oldshop-demo`フォルダで`ls .\build\libs`を実行して`oldshop.war`が作成されていることを確認。

### Tomcatへのデプロイ

1. PowerShell、たはVS Codeでメニューから「表示」→「ターミナル」を選択し、ビルドされたWARファイルをTomcatのWebアプリケーションフォルダに配置
```
cp .\build\libs\oldshop.war $env:CATALINA_HOME\webapps\. -Force	
```

2. PowerShell、たはVS Codeでメニューから「表示」→「ターミナル」を選択し、MySQLのJDBCドライバをTomcatのライブラリフォルダに配置
```
cp C:\workspace\Trial\CourseA\Java\oldshop-demo\tomcat_lib\mysql-connector-java-8.0.20.jar $env:CATALINA_HOME\lib\.
```

3. Tomcatの再起動
```
cd C:\workspace\tomcat\bin\
.\startup.bat
```

### デモアプリーケーションの動作確認

1. ブラウザにて`http://localhost:8080/oldshop/`にアクセスし下記が実行できることを確認。

* スタートページ
* ログイン
* メニュー
* 販売一覧
* 販売登録
* 商品選択
* 商品削除
* 販売変更
* 販売キャンセル

2. 上記を実行しながら、MySQL x.x Command Line 起動するか、VS Codeで「アクティブバー」より「SQLTool」を選択。`vs_con_flower_db `→`flower_db`→`Tables`→`sales`（販売テーブル）と`sales_detail`（販売明細テーブル）それぞれの右にある`Show Table Records`をクリックし、テーブルの更新状況を確認する。

### デモアプリーケーションのソースコード確認

1. VS Codeを起動しメニューの「ファイル」→「フォルダを開く」で`C:¥workspace¥oldshop-demo`フォルダを開きソースコードを開く。
 
2. デモアプリケーションの構造をデモアプリケーション解説」を参照しながら確認

* デモアプリケーション解説
[デモアプリケーション解説](https://github.com/ofp-training-admin/oldshop-demo/blob/master/DEMOAPP.md)

* シーケンス図ーシーケンスはSpring Bootで自動化されている箇所まで汎用的に記載している
[デモアプリケーション・シーケンス図](https://github.com/ofp-training-admin/oldshop-demo/blob/master/SEQUENCE.png)

3. マシンスペックによってはTomcat for JavaでWARファイルを読み込みブラウザで動作させながらデバッグモードでソースコードにブレイクポイントを置いて実行できる。
	
* `Crtl＋Shift＋P`でコマンドパレットを開き、「tomcat」と入力。「Add Tomcat Server」を選び、`C:¥workspace¥tomcat`を選択。
* 「エクスプローラ」の右下に「TOMCAT SERVERS」に追加された`tomcat`を右クリックし、「Debug WAR Package」を選択。`C:¥workspace¥oldshop-demo¥build¥libs¥oldshop.war`を選択しTomcatを起動。
* 任意の場所（例えばjp.core.ui.MainServer#serviceメソッドはHTTPリクエストを最初に受ける）にブレイクポイントを置く。
* ブラウザにて`http://localhost:8080/oldshop/`にアクセスしログイン画面表示以降ブレイクダウンで停止しデバッグできることを確認。
	
#OpenFastPath/Training/Trial