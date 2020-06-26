# OpenFastPath環境構築VSCode共通設定
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従いご利用のマシンスペックに合わせた環境構築手順によりGthubからコンテンツのダウンロードとマシンごとの補正が完了していること。
* 「OpenFastPath環境構築Docker設定共通」に従いDockerコンテナが生成され起動済みであること。（Windows直インストール版を除く）

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. Visual Studio Codeのリモート接続設定＜30分＞
2. Java・Spring Boot開発環境の設定＜30分＞

## Visual Studio Codeのリモート接続設定
[想定所要時間 30分]

1. 日本語パックのインストール

* 「アクティブバー」（左側に縦に表示されているアイコンのメニュー）から「拡張機能」（アイコンの上にマウスをおくとExtentionと表示される）を選択し、検索窓に”japan”を入力。（「拡張機能」は`Shift＋Ctrl/Command＋x`で起動できる）
* 検索結果よりJapanese Language Pack for VS Codeを選択しインストール。
	
```
-- Tips VS Codeの便利なショートカットメニュー --
VS Codeには多くのショートカットがあり、Shift＋Ctrl/Command＋xの他にも多くありマウスやメニュー選択よりも早く操作ができる。
例えば、Ctrl＋Shift＋Pで「コマンドパレット」は拡張機能をはじめ多くの機能の起動が可能である。
本文書中はVS Code上のどこにある操作なのかを覚えていただくためにあえて順番に実行する操作方法を示しているが、慣れてきたら併記されているショートカットを用いることが望ましい。		
```

2. リモート接続環境のインストール（Windows Home直インストール版除く）

* 「拡張機能」にて検索窓に”remote”を入力し、検索結果よりRemote Developmentを選択しインストール。
* 「アクティブバー」より「リモートエクスプローラー」を選び、リモートエクスプローラの右横にあるコンボボックスにて`CONTAINERS`を選択し、 Webサーバのコンテナ`flowerpj(/flower_web_nginx)`の右のアイコン「Attach to Container」をクリック。
* 「アクティブバー」から「エクスプローラ」を選択。「フォルダを開く」をクリックし、`/var/htdocs/`を指定してOKを選択しファイルが表示されることを確認。
* 「メニュー」→「表示」→「ターミナル」を選択し、`ls -ltr /var/log/nginx/`でnginxのログファイルが存在することを確認。
* リモートエクスプローラーにて アプリケーションサーバのコンテナ`flowerpj(/flower_app_springboot)`の右のアイコン「Attach to Container」をクリック。
* 「メニュー」→「表示」→「ターミナル」を選択し、`java -version`でJavaのバージョンを確認。

3. SSH接続の設定（Hyper-V / VMware / Raspberry PIのみ）

* 「アクティブバー」から「リモートエクスプローラー」を選び、リモートエクスプローラの右横にあるコンボボックスにて`SSH Targets`を選択し、 「TARGETS(SSH)」の右にある歯車ボタンをクリック。設定ファイル格納フォルダの選択にてそれぞれの環境のHomeディレクトリ配下の`.ssh/config`フォルダを選択して編集して保存。
```
Host 表示名として任意の名称（Ubuntu2004など）
	HostName 192.168.xx.xx （UbuntuのIPアドレス）
	User ubadmin
	IdentityFile ~/.ssh/id_rsa
```

* リモートエクスプローラの「TARGETS(SSH)」の下に登録された設定ファイルで`Host`に指定した名称が表示される。右にあるフォルダボタン「Connect to Host in New Windows」をクリックし、接続先OSタイプでLinuxを選択。新たに起動したVS Codeの画面のメニューから「表示」→「ターミナル」を選択。ターミナル右上の上矢印ボタンを押下して全画面表示にし操作する。

```
-- Tips SSHクライアントの利用 --
今回導入するLinuxディストリビューションであるUbuntu ServerはUbuntu Desktopと比べて軽量であり、Raspberry PI他でも軽量に動作するが、ホストOSとのコピーペーストの機能がないこともあるため、
PCからはVS Codeまたはターミナル、Power Shell(Power Shell ISE)など、iPhone、iPad、Androidなどモバイル端末からはTermiusなどのSSHクライアントからアクセスすると効率的である。）
```

4. Docker for VS Codeのインストールと設定（Windows Home直インストール版除く）

* 「拡張機能」の検索窓で「docker」を入力、「Docker for Visual Studio Code」をインストールする。
* UbuntuのDockerサービスのアクセスIPアドレスとポートを指定する。（Hyper-V／VMware／RaspberryPIのみ実施。Mac、WSL2はDocker for Windows/MacのDocker Client経由で接続するため設定なしで認識される。） 「アクティブバー」から「管理」→「設定」を選び、検索窓に`docker.host` と入力。「Docker :Host」に`tcp://192.168.xx.xx:59998`を指定。
* 「アクティブバー」から「Docker」を選択。CONTAINERSにDockerコンテナが表示され、コンテナごとの起動・停止が可能であること。（コンテナ名称横の再生・ストップそれぞれアイコンで可能）

5. SQLToolsのインストールと設定

* 「アクティブバー」から「Docker」を選択し、「flower_db」のコンテナが緑再生ボタンの稼働中になっているか確認。稼働中でない場合は右クリックしてStartを選択。 （Windows直インストール版は本手順不要）	
* 「拡張機能」の検索窓で”sqltool”と入力。	 検索結果からSQLToolsを選択しインストール。
* 「アクティブバー」から「拡張機能」を選択し、検索窓に”sqltool”と入力し、「アクティブバー」から「SQLTools MySQL/Maria DB」を選択肢インストール。
* 「アクティブバー」からSQLTool を選択。「CONNECTIONS」から「Add New Connection」のアイコンをクリックしMySQLを選択。（アクティブバーにSQLToolsが表示されない場合はコマンドパレット`Shift＋Ctrl/Command＋p`から「sql add」などと検索し「SQLTools Management : Add New Connection」を選択しても良い。）
* データベースの選択でMySQLを選び下記項目を設定。
	* Connection Name     vs_con_flower_db
	* Server Address 
		* Windows Home直接インストールの場合：localhost
		* WSL2 Ubuntu／Macの場合：localhost
		* Hyper-V ／ VMware／ RaspberryPIの場合：Ubuntuの IPアドレス
	* Port                            59306
	* Database                    flower_db
	* Username                   user
	* Password                    password
* 「TEST CONNECTION」を実行して「SUCCESS…」と表示されたら「SAVE CONNECTION」をクリック。
* 「CONNECTIONS」に追加された「vs_con_flower_db」をクリックし接続されることを確認。
* 「コマンドパレット」を開き”sqltools co”と入力。”SQLTools Connection: Run Query”を実行し`show databases;`を実行。flower_dbが表示されることを確認。

```
-- Tips VS CodeからMySQLへのアクセスのしくみ --
Dockerのサービスが動いているOS（WindowsではUbuntu、MacはMac OS自体）のIPアドレスを指定しても、Dockerサービス上で動作し、独自のIPアドレスを保持するコンテナにアクセスできるのは、docker-composeの設定ファイル（~/workspace/docker/compose/docker-compose_flowerapps.yml）でDockerサービスが動作するOSの特定のポートにアクセスされた処理をDockerコンテナのIPアドレスの特定のポートにそのまま転送する設定ーポートフォワーディングを指定しているからである。
セキュリティのためには、製品やミドルウェア、Linuxがデフォルトで使用するポートはできるだけ避け、別に設定することが望ましいが、本手順ではデフォルトのままとしている。
```

## Spring Bootサンプルコードの実行
[想定所要時間 30分]

1. 開発ルートフォルダを開く

* 環境に応じて、`flowershop`のJava、Spring Bootサンプルプログラムのルートフォルダを開く。
	* WSL2、Mac、Windows  Home直インストール版、Raspberry PIの場合
		* WSL2の場合「スタート」横の検索窓より「Ubuntu」を入力し「Ubuntu 20.04 LTS」を管理者権限で開く（WSL2の場合は後続のリモートエクスプローラからの実行も可能。）
		* Macの場合、ターミナルを開く
		* Windows  Home直インストール版の場合、Power Shell（またはPoser Shell ISE）を開く
		* Raspberry PIの場合、Lubuntuのデスクトップから「システムツール」→「LX Terminal」または「Q Terminal」を開く
		* それぞれの環境のターミナルにて `flowershop`のJava、Spring Bootサンプルプログラムのルートフォルダに移動し、VS Codeを起動。
		
```
cd ~/workspace/flowershop/code/javaapps/flowershop
code .
```

	* WSL2 / Hyper-V / VMware の場合
		* 「アクティブバー」より「リモートエクスプローラー」を選択。
		* WSL2の場合、リモートエクスプローラー右横のコンボボックスから「WSL Targets」を選び、WSLにインストールされている`Ubuntu 20.04 LTS`の右にあるフォルダボタン「Connect to Host in New Windows」をクリックし、Ubuntuに接続したVS Codeを新たに開く。
		* Hyper-V / VMware の場合、リモートエクスプローラー右横のコンボボックスから「SSH Targets」を選び、登録されたホスト名`ubuntu2004`の右にあるフォルダボタン「Connect to Host in New Windows」をクリックし、接続先OSタイプでLinuxを選択。Ubuntuに接続したVS Codeを新たに開く。
		* 新たに開いたVS Codeの 「エクスプローラー」から「フォルダを開く」をクリック。`/home/ubadmin/workspace/flowershop/code/javaapps/flowershop`を選択し「OK」をクリック。

```
-- Tips 初回起動 と以後の起動方法--
WSL2、Mac、Windows  Home直インストール版、Raspberry PIの場合、VS Codeをターミナルから起動しているが、初回はサーバ側のVS Codeの初期セットアップに時間を要するための措置である。

以後はそれぞれのメニューから起動して良い。
```

2. Java関連基本拡張機能のインストール

* 「拡張機能」を選択し 検索窓に下記インストール対象の名称の一部を入力。検索してインストール。指示に従い「再読み込み」を実施。
	* Java Extension Pack
	* Spring Boot Extension Pack
	* Lombok Annotations Support for VS Code

3. JAVA 環境の設定確認

* 各環境のターミナルでJDKインストール時に設定した環境変数JAVA_HOMEの値を確認する。
* 「アクティブバー」→「エクスプローラー」から「FLOWERSHOP」→`.vscode`→`setting.json`を開き、`java.home`の値がターミナルで確認した環境変数JAVA＿HOMEの値と一致していることを確認。
* 設定されていない場合、もしくは値が異なる場合は下記手順で設定する。
	* 左下の「管理」→「設定」を選択し、表示された検索窓で`java.home`を入力し、検索
		* 左メニューでJava(1)と表示されている箇所をクリックし、ワークスペースの「Java:Home」の 「setting.jsonで編集」 をクリックし、`setting.json`ファイルで ` "java.home": "環境変数JAVA＿HOMEで確認したJDKのインストールパス"`を追加し保存。

```
-- Tips VS Code上でもターミナルを動かせる --
＊VS Codeでメニューから表示ーターミナル を選択
＊ターミナルで ` echo $JAVA_HOME`の結果表示されるJavaのインストールディレクトリを確認。
＊ターミナルで`$JAVA_HOME/bin/java -version`を実行しバージョン情報が表示されることを確認 。（ここで存在しないなどのエラーが生じる場合問い合わせのこと。）
```

* 一度VS Codeを終了する。

4. サンプルコードのビルドとSpring Bootの起動

* 	「1. 開発ルートフォルダを開く」と同じ手順で開発のルートフォルダを開く。
* VS Codeのステータスバーの右下で回転マークが動いていたらマークをクリックし、ターミナル上で依存関係があるパッケージのダウンロードなどが全てDoneになるまで待つ。（※途中で止まるようであれば下記継続して実施して良い。）
* 「メニュー」ー「実行」ー「デバッグの開始（F5）」を実行し「Java」を選択。
* しばらく待つとターミナルにSpring Bootが起動されることを確認。（初回は時間がかかるため待つこと。）
* ブラウザで flowershopのトップページが表示されることを確認。
　　http://localhost:8080/

5. ホストOSからデータベースサーバへの接続確認

* 「アクティブバー」から「SQLTools」を選択
* 「CONNECTIONS」に表示されている「connection_flower_db」をクリック
```
Githubからダウンロードした~/workspace/flowershop/code/javaapps/flowershop/.vscode/settings.jsonに設定済みのためSQLToolを拡張機能をインストールするだけで接続可能になっている。
```			

6. Web開発を効率化する拡張機能のインストールと設定

* （Git操作の効率化）	
	* Git Extension Pack   Git操作の効率化
* （ドキュメント・図表効率化）
	* Markdown Preview Enhanced
	* Plant UML
* （HTML、CSSコーディング効率化）
	* HTML Snippets （HTMLを短い記述で補完）
	* IntelliSense for CSS class name in HTML（HTML上でCSSのクラス入力を支援）
	* HTMLHint（HTMLの構文チェック※Mike Kaufmanの方）
	* HTML CSS Support （CSSの候補を支援）
	* HTML Preview（VS Code上でHTMLをプレビュー）
* （Javaコーディング効率化）
	* Checkstyle for Java （Java コードチェック）
	* Java Linter （Java 構文チェック）
* （JavaScriptコーディング効率化）
	* ESLint      Java Script 構文チェック
	* Prettier - JavaScript formatter     Java Script フォーマッター

※EmnetはVS Codeに標準で組み込まれているため拡張せずに利用可能。
　[Emmet in Visual Studio Code](https://code.visualstudio.com/docs/editor/emmet)
		 
#OpenFastPath/Environment/Fixed

