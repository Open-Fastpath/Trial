# OpenFastPathトライアル環境構築Mac版
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンがMacを利用する場合に本手順を用いて環境を構築する。

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. Open JDKのインストール＜5分＞
2. Gradleのインストール＜3分＞
3. Graphvizのインストール＜2分＞
4. Dockerのインストール＜10分＞
5. Dockerサービスのビルドと起動＜10分＞
6. Visual Studio Codeのインストールと設定＜10分＞

### Open JDKのインストール

Visual Studio CodeからUbuntu上で開発しJavaの入っているコンテナへ今後のトレーニングでリリースをするため、ホストOS上にもOpenJDKをインストールする。

* 「Lanchpad」→「その他」→「ターミナルを選択し起動
* Home brewがインストールされていない場合は下記にてHome brewをインストール（管理者パスワードが聞かれるため入力する。）
※「bluestacks」がインストールされている場合アンインストールする
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
```

* brewにてAdoptOpenJDK11をインストール
```
brew tap AdoptOpenJDK/openjdk
brew cask install adoptopenjdk11
```

* OSがCatlinaの場合（アップルマークからこのMac についてで確認）ターミナルを起動し下記コマンドを実行し、設定ファイル.zprofileの最後に書き加えられたことを確認。
```
echo "export JAVA_HOME=\"/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home\"" >> .zprofile
source .zprofile
cat .zprofile
```

* OSがCatlina以前の場合はターミナルを起動し下記コマンドを実行し、設定ファイル.bashrcの最後に書き加えられたことを確認。
```
echo "export JAVA_HOME=\"/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home\"" >> .bashrc
source .bashrc
cat .bashrc
```

※MacはCatlinaよりシェルがZSHベースになっているため設定が異なる。

* インストール後、JAVA＿HOMEの環境変数で指定されているパスにOpenJDK11がインストールされたことを確認。
```
echo $JAVA_HOME
$JAVA_HOME/bin/java -version
```

###  Gradleのインストール

* Gradleをインストールする。バージョンが6以上であることを確認。（MacのHomeBrewでは最新版の6.xがダウンロードされる。）
```
brew install gradle
gradle -v
```

### Graphvizのインストール

* VS CodeでMarkdown上でPlantUMLを有効にするためにGraphvizのライブラリをインストールする。
```
brew install graphviz
```

### Dockerのインストール

* Docker HubのサイトにてDocker Hubのユーザーを作成し、Docker for Macをダウンロード。何も設定を変更しないでインストール。※インストーラの起動に時間がかかるが待つこと。
[Docker Hub](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)
* Docker for Macを起動。※通知領域にDockerのアイコンが表示されるまで時間がかかるが待つこと。

* dockerをインストール。※docker-composeも導入される。
```
brew install docker
```

* ターミナルを起動し`docker version`にてClientとServer両方が表示されることを確認。

### Dockerサービスのビルドと起動

1. トライアル環境のダウンロード

* Open Fast PathのGitからトライアルコンテンツをダウンロード
```
cd
git clone https://github.com/Open-Fastpath/Trial.git
```

* コンテナのlog保管ディレクトリを作成する。
* DBサーバコンテナ用にMySQLのログファイルを作成し権限を付与。

2. Docker実行環境追加設定

* コンテナのlog保管ディレクトリを作成する。
* DBサーバコンテナ用にMySQLのログファイルを作成し権限を付与。
```
cd
mkdir -p workspace/flowershop/log/mysql
cd ~/workspace/flowershop/log/mysql
touch mysqld.log
chmod 666 mysqld.log
```

* トライアル環境のファイルを配置。
```
cd
cp -rp  ~/Trial/CourseA/Java/flowershop-trial-env/docker workspace/flowershop/.
```

3. Dockerイメージのビルドとコンテナの起動

* docker-composeを利用しGithubからダウンロードしたdocker-compose用のymlファイルのコンテナの定義に基づきDockerのイメージをビルドしコンテナを起動。（"bash c1"のみ入力しTabキーを押すとファイル名が補完される。）
```
cd ~/workspace/flowershop/docker/util
bash c1-build_and_start_compose.sh
```

4. Dockerのコンテナサービスの動作確認

* サーバの起動状態を確認する。 コンテナのステータスがいずれも”Up”となっていることを確認。
```
cd ~/workspace/flowershop/docker/util
bash c2-view_compose_status.sh
```

* コンテナのログ出力を確認する。全てのコンテナのログが記録されていれば良い。
```
cd ~/workspace/flowershop/docker/util
bash c3-view_compose_log.sh
```

### Visual Studio Codeのインストールと設定

* Visual Studio Code（以後VS Code）のダウンロードサイトから利用OSに合わせたインストーラをダウンロードし実行。
[Download Visual Studio Code - Mac, Linux, Windows](https://code.visualstudio.com/download)
* インストール完了画面でVisual Studio Codeを起動するを選択しVS Codeを起動。
```
ーVS Codeのクリーンインストールー
VS Codeはアンインストールしただけでは拡張機能や設定のフォルダが削除されないため、下記のフォルダも削除する。

rm -rf '/Applications/Visual Studio Code.app/'
rm -rf '~/Library/Application Support/Code'
rm -rf ~/.vscode/
```

2. 日本語パックのインストール

* 「アクティブバー」（左側に縦に表示されているアイコンのメニュー）から「拡張機能」（アイコンの上にマウスをおくとExtentionと表示される）を選択し、検索窓に”japan”を入力。（「拡張機能」は`Shift＋Ctrl/Command＋x`で起動できる）
* 検索結果よりJapanese Language Pack for VS Codeを選択しインストール。

3. Docker for VS Codeのインストールと設定

* 「拡張機能」の検索窓で「docker」を入力、「Docker for Visual Studio Code」をインストールする。
* 「アクティブバー」から「Docker」を選択。CONTAINERSにDockerコンテナが表示され、コンテナごとの起動・停止が可能であること。（コンテナ名称横の再生・ストップそれぞれアイコンで可能）

4. SQLToolsのインストールと設定

* 「アクティブバー」から「Docker」を選択し、「flower_db」のコンテナが緑再生ボタンの稼働中になっているか確認。稼働中でない場合は右クリックしてStartを選択。 	
* 「拡張機能」の検索窓で”sqltool”と入力。	 検索結果からSQLToolsを選択しインストール。
* 「アクティブバー」から「拡張機能」を選択し、検索窓に”sqltool”と入力し、「アクティブバー」から「SQLTools MySQL/Maria DB」を選択肢インストール。
* 「アクティブバー」からSQLTool を選択。「CONNECTIONS」から「Add New Connection」のアイコンをクリックしMySQLを選択。（アクティブバーにSQLToolsが表示されない場合はコマンドパレット`Shift＋Ctrl/Command＋p`から「sql add」などと検索し「SQLTools Management : Add New Connection」を選択しても良い。）
* データベースの選択でMySQLを選び下記項目を設定。
	* Connection Name     vs_con_flower_db
	* Server Address         localhost
	* Port                            59306
	* Database                    flower_db
	* Username                   user
	* Password                    password
* 「TEST CONNECTION」を実行して「SUCCESS…」と表示されたら「SAVE CONNECTION」をクリック。
* 「CONNECTIONS」に追加された「vs_con_flower_db」をクリックし接続されることを確認。
* 「コマンドパレット」を開き”sqltools co”と入力。”SQLTools Connection: Run Query”を実行し`show databases;`を実行。flower_dbが表示されることを確認。

5. Web開発関連拡張機能のインストールと設定

* 「拡張機能」を選択し 検索窓に下記インストール対象の名称の一部を入力。検索してインストール。指示に従い「再読み込み」を実施。
	* Java Extension Pack
	* Spring Boot Extension Pack
	* Lombok Annotations Support for VS Code

#OpenFastPath/Environment/Trial
