#  OpenFastPathトライアル環境構築Windows直インストール版
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンがWindows Home 32bit版もしくはBIOSの仮想化が不可の場合に本手順を用いて環境を構築する。

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. OpenJDKのインストール＜5分＞
2. Gradleのインストール＜3分＞
3. Graphvizのインストール＜2分＞
4. MySQLのインストールと設定＜20分＞
5. Gitクライアントのインストール＜5分＞
6. Visual Studio Codeのインストールと設定＜10分＞

### OpenJDKインストール

* `C:¥workspace¥jdk`フォルダを作成。
* OpenJDK11を[AdoptOpenJDK - Open source, prebuilt OpenJDK binaries](https://adoptopenjdk.net/)にアクセスし、OpenJDK 11 (LTS)のHotSpot版を選択し「Latest Release」をクリックしてダウンロード
* ダウンロードしたインストーラを起動しインストール。インストール先フォルダを`C:¥workspace¥jdk`に変更。
* 「スタート」の横の検索窓で「システム環境変数」と入力し「システム環境変数の編集」をクリック。「システムのプロパティ」で「環境変数」をクリック。「システム環境変数」の「新規」をクリック。現れたダイアログで「変数名」に`JAVA_HOME`「変数値」は「ディレクトリの参照」をクリックし`C:¥workspace¥jdk`を選択。「OK」をクリック。以後の画面を終了する。

### Gradleのインストール

* `C:¥workspace¥gradle`フォルダを作成。
* [Gradle | Releases](https://gradle.org/releases/)よりv6.５のDownloadから「binary-only」をクリックし`gradle-6.5-bin.zip`をダウンロード。解凍した`gradle-6.5-bin¥gradle-6.5`フォルダの下を`C:¥workspace¥gradle`に移動。
* 	「スタート」の横の検索窓で「システム環境変数」と入力し「システム環境変数の編集」をクリック。「システムのプロパティ」で「環境変数」をクリック。「環境変数」ボタンをクリックし、「ユーザー環境変数」の「新規」をクリック。現れたダイアログで「変数名」に`GRADLE_HOME`「変数値」は「ディレクトリの参照」をクリックし`C:¥workspace¥gradle`を選択。
* 	「システム環境変数」より「Path」を選択し「編集」をクリック。現れたダイアログで「新規」で行を追加。「参照」をクリックし`C:¥workspace¥gradle¥bin`を選択。「OK」をクリック。以後の画面を終了する。
* Power Shellを開き、`gradle -v`でGradleのバージョンが6.x以上で表示されることを確認。（初回は時間がかかるが待つ）

### Graphvizのインストール

* [AppVeyor](https://ci.appveyor.com/project/ellson/graphviz-pl238/builds/32032002/job/yih2iyesctaji7wa/artifacts)よりzipファイルをダウンロード。（サイト構造が変わり不可の場合は、[Graphviz - Graph Visualization Software](http://www.graphviz.org/)→「Download」→「Stable .... Windows install packages」→ 「Environment: build_system=msbuild; Configuration: Release」→ 「Artifacts」にてzipダウンロードにたどる。） 
* ダウンロードしたZIPを解凍し、配下の`Graphviz`フォルダを`C:¥workspace`にコピー。
* スタート」の横の検索窓で「システム環境変数」と入力し「システム環境変数の編集」をクリック。「システムのプロパティ」で「環境変数」をクリック。「環境変数」ボタンをクリックし、「ユーザー環境変数」の一覧から「Path」を選択。「編集」をクリック。現れたダイアログで「新規」で行を追加。「参照」をクリックし`C:¥workspace¥Graphviz¥bin`を選択。「OK」をクリック。以後の画面を終了する。
* Power Shellを開き直し、`dot -v`でGraphvizのバージョンが表示されることを確認。`Ctrl+c`で終了。

### MySQLインストールと設定

*  `C:¥workspace¥mysql¥log`フォルダを作成。
* [MySQL Installer for Windows](https://dev.mysql.com/downloads/windows/) よりversion8.0.2 32bit向けインストーラをダウンロードしインストール。
* InstallerのUpgradeが聞かれるが「No」
* 「Server Only」を選択し「Next」
* 「Check Requiirement」で全て選択して「Execute」を実行し、前提となるライブラリをインストールしてから「Next」をクリック。
* High Availabilityは「Standard MySQL Server / Classic MySQL Replication」を選択し「Next」
* Type and Networkingは「Port」を「59306」に変更。「Show Advanced and Logging Option」にチェックを入れて「Next」
* （MySQL8.0以上の場合）Authentication Methodで「Use Legacy Authentication Method」を選択。
* Accounts and Rolesは、下記設定し「Next」
	* Root password     mysqladminpw
	* 「Add User」でユーザID：user、パスワード：passwordのユーザを追加
* Windows Serviceはデフォルトのまま「Next」
* Logging Optionsで下記を設定。いずれも入力欄右の参照ボタンで`C:¥workspace¥mysql¥log`フォルダを選択しファイル名を入力。
	* Error Log      `mysql.err.txt`をファイル名にする。
	* General Log   チェックを入れ、`mysql.general.txt`をファイル名にする。
	* 	Slow Query Log    `mysql-slow.txt`をファイル名にする。
* 以後はデフォルトのまま進み「Execute」でインストール。
* インストール完了後、Windowsでのデータが扱えるようサーバの言語設定を`latin1`から`utf8mb4`に変更する。`C:¥ProgramData¥MySQL¥MySQL Server 8.0¥my.ini`の`character_set_server=utf8mb4`を追記して保存する。（隠しフォルダであるため、エクスプローラの表示で隠しフォルダを表示できる様にする。）
```
# The default character set that will be used when a new schema or table is
# created and no character set is defined
# character-set-server=
character_set_server=utf8mb4
```

* （MySQL8.0以上の場合）インストール中にAuthentication Methodで「Use Legacy Authentication Method」を選択できなかった場合、ユーザIDとパスワードの認証方式ができるよう`C:¥ProgramData¥MySQL¥MySQL Server 8.0¥my.ini`を変更する。
```
[mysqld]
default-authentication-plugin=mysql_native_password
```

* 「スタート」の横の検索窓で「task」と入力し「タスクマネージャー」を起動。「詳細」をクリックし、表示されたタブから「サービス」タブを選択。「MySQLx.x」を選び右クリックで「再起動」をクリック。「状態」が「実行中」に変わるまで待つ。
* 「スタート」の横の検索窓で「MySQL」と入力し「MySQLx.x Comand Line Client」を起動。インストール時に設定したRoot passwordでログイン。
* mysqlのコマンドラインになったら`create database flower_db;`を実行しデータベースを作成。
* `GRANT ALL ON flower_db.* TO user;`でユーザー`user`に全権限を付与。
* `show variables like "cha%";`で`character_set_server `が`utf8mb4`になっていることを確認。

```
+--------------------------+---------------------------------------------------------+
| Variable_name            | Value                                                   |
+--------------------------+---------------------------------------------------------+
| character_set_client     | cp932                                                   |
| character_set_connection | cp932                                                   |
| character_set_database   | utf8mb4                                                 |
| character_set_filesystem | binary                                                  |
| character_set_results    | cp932                                                   |
| character_set_server     | utf8mb4                                                 |
| character_set_system     | utf8                                                    |
| character_sets_dir       | C:\Program Files\MySQL\MySQL Server x.x\share\charsets\ |
+--------------------------+---------------------------------------------------------+
```

*（MySQL8.0以上の場合）ユーザー`user`が従来のMySQLのユーザID、パスワード方式で認証できるようにする。
```
mysql>SELECT user, host, plugin from mysql.user
(ここでuser@%の認証方式pluginがmysql_native_passwordか確認)
mysql> ALTER USER 'user'@'%' IDENTIFIED WITH mysql_native_password BY 'password';
mysql> FLUSH PRIVILEGES;
mysql> quit
```

### Gitクライアントのインストール

* [Git for Windows](https://gitforwindows.org/)より「ダウンロード」をクリックし、インストーラを起動。デフォルト設定を変更せずインストール。

### Visual Studio Codeのインストールと設定

* Visual Studio Code（以後VS Code）のダウンロードサイトから利用OSに合わせたインストーラをダウンロードし実行。
[Download Visual Studio Code - Mac, Linux, Windows](https://code.visualstudio.com/download)
* インストール完了画面でVisual Studio Codeを起動するを選択しVS Codeを起動。
```
ーVS Codeのクリーンインストールー
VS Codeはアンインストールしただけでは拡張機能や設定のフォルダが削除されないため、下記のフォルダも削除する。
C:¥Users¥<ユーザー名>¥AppData¥Local¥Programs¥VS Code
C:¥Users¥<ユーザー名>¥AppData¥Roaming¥Code
C:¥Users¥<ユーザー名>¥.vscode
```

2. 日本語パックのインストール

* 「アクティブバー」（左側に縦に表示されているアイコンのメニュー）から「拡張機能」（アイコンの上にマウスをおくとExtentionと表示される）を選択し、検索窓に”japan”を入力。（「拡張機能」は`Shift＋Ctrl/Command＋x`で起動できる）
* 検索結果よりJapanese Language Pack for VS Codeを選択しインストール。

3. SQLToolsのインストールと設定

* 「アクティブバー」から「拡張機能」を選択し、検索窓で”sqltool”と入力。	 検索結果から「SQLTools」を選択しインストール。
* 「アクティブバー」から「拡張機能」を選択し、検索窓に”sqltool”と入力。検索結果から「SQLTools MySQL/Maria DB」を選択しインストール。
* 「アクティブバー」からSQLTool を選択。「CONNECTIONS」から「Add New Connection」のアイコンをクリックしMySQLを選択。（アクティブバーにSQLToolsが表示されない場合はコマンドパレット`Shift＋Ctrl/Command＋p`から「sql add」などと検索し「SQLTools Management : Add New Connection」を選択しても良い。）
* データベースの選択でMySQLを選び下記項目を設定。
	* Connection Name     vs_con_flower_db
	* Server Address          localhost
	* Port                            59306
	* Database                    flower_db
	* Username                   user
	* Password                    password
* 「Add New Connection」が実行できない場合は、「アクティブバー」より左下の「管理」→「設定」を選択し、検索窓に「sqltool connection」と入力。「SQLTools Setting」をクリックし、「Sqltools: Connections」にて「settings.jsonで編集」をクリック。エディタで下記となるように記載し保存。
```
{
    "sqltools.connections": [
    {
        "database": "flower_db",
        "driver": "MySQL",
        "name": "connection_flower_db",
        "password": "password",
        "port": 59306,
        "server": "localhost",
        "username": "user"
    }
    ],   
}
```
* 「TEST CONNECTION」を実行して「SUCCESS…」と表示されたら「SAVE CONNECTION」をクリック。
* 「CONNECTIONS」に追加された「vs_con_flower_db」をクリックし接続されることを確認。
* 「コマンドパレット」を開き”sqltools co”と入力。”SQLTools Connection: Run Query”を実行し`show databases;`を実行。flower_dbが表示されることを確認。

4. Web開発関連拡張機能のインストールと設定

* 「拡張機能」を選択し 検索窓に下記インストール対象の名称の一部を入力。検索してインストール。指示に従い「再読み込み」を実施。
	* Java Extension Pack
	* Spring Boot Extension Pack
	* Lombok Annotations Support for VS Code

#OpenFastPath/Environment/Trial
