# OpenFastPathトライアルトレーニング 
-ー　 version 2020.06.26　ー

## 前提

* 本資料は「OpenFastPathトライアル環境構築VMware版 」「OpenFastPathトライアル環境構築Mac版」の手順に従いトレーニング環境の構築が完了していることを前提とした手順である。

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
	* ブラウザで`http://localhost:8090/`で「トップページ」画面表示。（Tomcatなどとポートが衝突しないようにSpring Bootは8090ポートで起動するようにしている。変更する際にはVS Code上から`src/main/java/resources/application.properties`を開き、server.port=8090）を変更する）
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

* Githubよりダウンロードしたトライアル用ソースコードを開発用のフォルダに配置。
```
cd ~/workspace
cp -rp ~/Trial/CourseA/Java/flowershop-trial .
```

### データベースのデータ登録状況確認

* トライアル環境では、環境構築にて、MySQLのデータベースサーバDockerコンテナを生成する際に、今回利用するデータベース、テーブル、テストデータを自動でセットしている。本トレーニングではテーブルやデータの登録も学ぶが、トライアルではデータベースの利用方法を理解いただく。
* 「アクティブバー」から「Docker」を選択し、「flower_db」のコンテナが緑再生ボタンの稼働中になっているか確認。稼働中でない場合は右クリックしてStartを選択し、再生ボタンとなり稼働したことを確認。 	
* 「アクティブバー」から「SQL Tools」を選択し、「CONNECTIONS」→「vs_con_flower_db」→「flower_db」→「Tables」を開き`sales`と`sales_detail`の２つのテーブルが作成されていることを確認。
*「CONNECTIONS」→「vs_con_flower_db」→「flower_db」→「Tables」を開き`sales`と`sales_detail`の２つのテーブルが存在することを確認。
* `sales` `sales_detail`それぞれの右側にある「Show Table Records」をクリック。レコードが登録されていることを確認する。

### 実装・ビルド・稼働確認

1. ソースコードフォルダのオープン・実装とデバッグ

* VS Codeの「アクティブバー」から「エクスプローラ」を選び「フォルダを開く」でVS Codeでソースコードのルートディレクトリ`~/workspace/flowershop-trial/code/javaapps/flowershop`のフォルダ開く。
* 「エクスプローラ」で`.vscode`→`setting.json`を開き`java.home`がJDKのインストールディレクトリと一致していることを確認。一致していない場合は修正して保存。
*  VS Codeのステータスバーの右下で回転マークが動いていたらマークをクリックし、ターミナル上で依存関係があるパッケージのダウンロードとビルドが全てDoneになるまで待つ。（※途中で止まるようであれば下記継続して実施して良い。）
* 今回のテーマ「販売を一覧で見えるようにする」を実装し保存する。
* VS Codeの「表示」→「問題」にて VS Codeの自動コンパイルがなされて検知できる問題がリアルタイムに表示される。
* 表示箇所をクリックすると問題箇所にジャンプするため対象箇所を修正する。
* 問題によっては問題箇所にマウスオーバーするとクイックフィックスのリンクが表示されることがある。クイックフィックスの不具合修正が妥当かは人による判断が必要なものもあるため確認せずに反映しないように注意すること。
	
```
ーーVS Codeの自動検知の問題ーー
開発対象のプログラム自体が参照可能でありプログラムも動作するが
「シンボルを見つけられません」「パッケージ xxx は存在しません」となる現象がある。VS Codeの問題で警告表示されるがこれは無視をして良い。
それ以外の問題や警告はすべて修正する。
```

2. 実装後のビルドとデバッグ
	
* `Ctrl/Command+Shift+p`でコマンドパレットを開き`task`と入力。タスク：ビルドタスクの実行を選択し、ターミナルでGradleのbuildタスクを実行する。`BUILD SUCCESSFUL`が表示されたら次のSpring Bootを実行する`BUILD FAILED`が表示されたらログから原因を解析し修正する。
	
```
ーVS Codeのタスクの設定ー
ビルドタスクは`.vscode/tasks.json`でカスタマイズできる。上記ビルドタスクの実装は`tasks.json`で`isDefault": true`がされているタスクがデフォルトで実行される。個別に指定して実行する場合はVS Codeのメニューから「ターミナル」→「タスクの実行」をすると`tasks.json`の`”label”`が表示され、選択すると`"command"`で定義されているコマンドが実行される。
```
	
3. .稼働確認

* VS Codeのメニューで「実行」→「デバッグの開始」を選択。
* 起動される「デバッグコンソール」（自動起動されない場合はVS Codeのメニューで「表示」→「デバッグコンソール」を選択し表示）でSpring Bootが起動されることを確認。
* ブラウザにて`http://localhost:8090/`（VMware、Hyper-VはlocalhostをUbuntuのIPアドレスに置き換え）でトップページを表示し、ログイン画面（ユーザID：tencyo  パスワード：ohanayasan）→メニュー画面→販売一覧画面に遷移しデータが表示されることを確認。 

- - - -


## スケルトンプログラムを利用する（希望者のみ）

1. スケルトンプログラムの反映
スケルトンプログラムは、「トライアル用のソースコードのダウンロード」でダウンロードしたファイルに含まれている。下記手順でスケルトンプログラムも作業ディレクトリに反映する。
```
cd ~workspace/flowershop-trial
cp -rp skeleton/code .
```

2. スケルトンプログラムでの実装
	
* 実装対象オブジェクト
※VS Code上は`src/main/java/jp/flowershop`配下のパスから記載
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

* 	実装手順は上記プログラム上の実装箇所に`[FOR TRIAL`のキーワードで詳細な実装方法を記載しているため記述の通りに実装する。	
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

### Tomcat のインストール

1. ターミナルにてtomcatユーザを追加
（Windows：Ubuntuのターミナル、Mac：通常のターミナルで実行）
```
sudo groupadd tomcat
sudo useradd -s /bin/false -g tomcat -d /usr/local/tomcat tomcat
sudo usermod -aG tomcat $USER
sudo gpasswd -a $USER tomcat
(一度ログアウトして再度ログインしてグループ登録反映)
```

2. Tomcatをダウンロードし配置. 
```
cd /usr/local

sudo wget http://archive.apache.org/dist/tomcat/tomcat-9/v9.0.34/bin/apache-tomcat-9.0.34.tar.gz

sudo tar xvf apache-tomcat-9.0.34.tar.gz

sudo ln -s apache-tomcat-9.0.34 tomcat
sudo chown -R tomcat.tomcat apache-tomcat-9.0.34 

cd tomcat
sudo chmod 770 conf logs lib webapps temp work
sudo chmod 644 conf/*
sudo chmod 664 logs/*
```

3. Tomcatの管理機能をインストール
```
sudo apt-get install tomcat9-admin -y
```

4. Tomcat管理Webの管理者ユーザを追加
 `/usr/local/tomcat/conf/tomcat-users.xml`の最終行`</tomcat-users>`の前に下記を追加する
```
<user username="tomcatadmin" password="tomcatadmin" roles="manager-gui,admin-gui"/>
```

5. Tomcat管理Webのアクセス可能IPを追加（デフォルトでは127.0.0.1のみアクセス可能となっているため仮想ネットワークでアクセスするVMware、Hyper-VはローカルIPからのアクセスを許可する様に追加する必要がある。）
 `/usr/local/tomcat/webapps/manager/META-INF/context.xml`の`allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />`の行を下記に編集する。（下記では`192.168.0.0/24`のIPアドレスでのアクセスを許可している。）
```
allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1|192\.168\.\d+\.\d+" />
```

6.  Tomcat を起動する
※起動中の場合は`bash catalina.sh stop`を実行してから`start `を実行する。
```
cd /usr/local/tomcat/bin
bash startup.sh
(起動後に作成されたファイルのオーナーを変更)
sudo chown tomcat.tomcat ../logs/*
sudo chmod 664 ../logs/*
```

6. ブラウザで確認する。

* `http://localhost:8080/`（VMware、Hyper-VはlocalhostをUbuntuのIPアドレスに置き換え）でTomcatのWelcome画面の表示を確認。
* `http://locahost:8080/manager`（VMware/Hyper-V/Raspvberry PIはlocalhostをUbuntuのIPアドレスに置き換え）にアクセスし、ログインのダイアログで4.で指定したユーザIDとパスワードを入力し、管理者画面が表示されることを確認。

### デモプログラムのビルド

1. デモプログラムの配置

* Power Shellを起動し、Githubよりダウンロードしたデモプログラムのソースコードを開発用のフォルダに配置。
```
cd ~/workspace
cp -rp ~/Trial/CourseA/Java/oldshop-demo .
```

2. デモアプリケーションをGradleでビルド
「ターミナル」でビルドツールgradleを用い、WARファイル（Web application ARchive）を生成する。（事前に配布されているbuild.gradleの設定に従いコンパイルからパッケージングまで実行される）
```
cd oldshop-demo
gradle build
gradle war
```

```
ーWAR（Web application ARchive）ファイルー
WARファイルとは、Webアプリケーションを動作させるのに必要な、HTMLファイル、CSSファイル、JavaScriptファイル、javaなどのサーバプログラム、実行に必要なライブラリなどの一連の構成を全てパッケージし、ディレクトリ構成ごと圧縮してデプロイーアプリケーションの配布・リリースを容易に実施するための仕組みである。

TomcatなどのWebアプリケーションサーバでは、WARファイルをWeb アプリケーションを配置するフォルダに置くと、自動的に展開して実行可能となる。
通常はアプリケーションのコンテキストルート（Webからアクセスした場合にhttp://ドメイン名/でアクセスできるルートディレクトリ）の直下にWARファイルを配置すると、http://ドメイン名/(WARファイル名の.warを除いた名称)でアクセスすることができる。
```

* 「gradle」の実行結果として`BUILD SUCCESSFUL`が表示されたら、
`ls -ltr build/libs`により`oldshop.war`が作成されていることを確認。

### Tomcatへのデプロイ

1. ビルドされたWARファイルをTomcatのWebアプリケーションフォルダに配置
```
cp -p ~/workspace/oldshop-demo/build/libs/oldshop.war /usr/local/tomcat/webapps/.
```

2. MySQL/MariaDBのJDBCドライバをTomcatのライブラリフォルダに配置（Raspberry PIの場合は`mysql-connector-java-8.0.20.jar`の代わりに`mariadb-java-client-2.6.0.jar`を配備する。）
```
sudo cp -p ~/Trial/CourseA/Java/oldshop-demo/tomcat_lib/mysql-connector-java-8.0.20.jar /usr/local/tomcat/lib/. 
sudo chown tomcat.tomcat /usr/local/tomcat/lib/mysql-connector-java-8.0.20.jar 
```

3. Tomcatの再起動
```
cd /usr/loca/tomcaat/bin
bash shutdown.sh
bash startup.sh
```

### デモアプリーケーションの動作確認

1. ブラウザにて`http://localhost:8080/oldshop/`にアクセスし下記が実行できることを確認。

* スタートページ
* ログイン （ユーザID：tencyo  パスワード：ohanayasan）
* メニュー
* 販売一覧
* 販売登録
* 商品選択
* 商品削除
* 販売変更
* 販売キャンセル

2. 上記を実行しながら、VS Codeを立ち上げ、「アクティブバー」より「SQLTool」を選択。`vs_con_flower_db `→`flower_db`→`Tables`→`sales`（販売テーブル）と`sales_detail`（販売明細テーブル）それぞれの右にある`Show Table Records`をクリックし、テーブルの更新状況を確認する。

### デモアプリーケーションのソースコード確認


1. VS Codeの「アクティブバー」から「エクスプローラ」を選び「フォルダを開く」でVS Codeでソースコードのルートディレクトリ`~/workspace/oldshop-demo`のフォルダ開く。

2. デモアプリケーションの構造をデモアプリケーション解説」を参照しながら確認

* デモアプリケーション解説
[デモアプリケーション解説](https://github.com/ofp-training-admin/oldshop-demo/blob/master/DEMOAPP.md)

* シーケンス図
シーケンスはSpring Bootで自動化されている箇所まで汎用的に記載している
[デモアプリケーション・シーケンス図](https://github.com/ofp-training-admin/oldshop-demo/blob/master/SEQUENCE.png)

#OpenFastPath/CourseA/Java/Trial