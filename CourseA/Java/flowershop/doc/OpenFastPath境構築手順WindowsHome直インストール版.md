#  OpenFastPath境構築手順WindowsHome直インストール版
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンがWindows Home 32bit版もしくはBIOSの仮想化が不可の場合に本手順を用いて環境を構築する。
* 本手順の後に「OpenFastPath環境構築VSCode共通設定」を実施する。 

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. OpenJDKのインストール
2. Gradleのインストール
3. Graphvizのインストール
4. MySQLのインストール
5. Gitクライアントのインストール
6. Visual Studio Codeのインストール

## OpenJDKインストール

* `C:¥workspace¥jdk`フォルダを作成。
* OpenJDK11を[AdoptOpenJDK - Open source, prebuilt OpenJDK binaries](https://adoptopenjdk.net/)にアクセスし、OpenJDK 11 (LTS)のHotSpot版を選択し「Latest Release」をクリックしてダウンロード
* ダウンロードしたインストーラを起動しインストール。インストール先フォルダを`C:¥workspace¥jdk`に変更。
* 「スタート」→「設定」→「システム」→「バージョン情報」→関連情報の「システム情報」をクリックし、左メニューの「システムの詳細設定」をクリック。「環境変数」ボタンをクリックし、「システム環境変数」の「新規」をクリック。現れたダイアログで「変数名」に`JAVA_HOME`「変数値」は「ディレクトリの参照」をクリックし`C:¥workspace¥jdk`を選択。「OK」をクリック。以後の画面を終了する。

### Gradleのインストール

* `C:¥workspace¥gradle`フォルダを作成。
* [Gradle | Releases](https://gradle.org/releases/)よりv6.５のDownloadから「binary-only」をクリックし`gradle-6.5-bin.zip`をダウンロード。解凍した`gradle-6.5-bin¥gradle-6.5`フォルダの下を`C:¥workspace¥gradle`に移動。
* 	「スタート」→「設定」→「システム」→「バージョン情報」→関連情報の「システム情報」をクリックし、左メニューの「システムの詳細設定」をクリック。「環境変数」ボタンをクリックし、「ユーザー環境変数」の「新規」をクリック。現れたダイアログで「変数名」に`GRADLE_HOME`「変数値」は「ディレクトリの参照」をクリックし`C:¥workspace¥gradle`を選択。
* 	「システム環境変数」より「Path」を選択し「編集」をクリック。現れたダイアログで「新規」をクリックし「参照」をクリックし`C:¥workspace¥gradle¥bin`を選択。「OK」をクリック。以後の画面を終了する。
* Power Shellを開き、`gradle -v`でGradleのバージョンが6.x以上で表示されることを確認。

### Graphvizのインストール

* `C:¥workspace¥graphviz`フォルダを作成。
* [AppVeyor](https://ci.appveyor.com/project/ellson/graphviz-pl238/builds/32032002/job/yih2iyesctaji7wa/artifacts)よりzipファイルをダウンロード。（サイト構造が変わり不可の場合は、[Graphviz - Graph Visualization Software](http://www.graphviz.org/)→「Download」→「Stable .... Windows install packages」→ 「Environment: build_system=msbuild; Configuration: Release」→ 「Artifacts」にてzipダウンロードにたどる。） 
* ダウンロードしたZIPを解凍し、配下の`Graphviz`フォルダを`C:¥workspace`にコピー。
* 「スタート」→「設定」→「システム」→「バージョン情報」→関連情報の「システム情報」をクリックし、左メニューの「システムの詳細設定」をクリック。「環境変数」ボタンをクリックし、「ユーザー環境変数」の一覧から「Path」を選択。「編集」をクリック。現れたダイアログで「新規」をクリックし、一覧の最終行に`C:¥workspace¥Graphviz¥bin`を追加して「OK」をクリック。以後の画面を終了する。
* Power Shellを開き、`dot -v`でGraphvizのバージョンが表示されることを確認。

### MySQLインストール

* [MySQL Installer for Windows](https://dev.mysql.com/downloads/windows/) よりversion8.0.2 32bit向けインストーラをダウンロードしインストール。（もしくは[MySQL :: Download MySQL Installer (Archived Versions)](https://downloads.mysql.com/archives/installer/)より製品バージョンを5.7.xを選択しダウンロードしインストールする。）
* 「Server Only」を選択し「Next」
* 「upgrade now?」と聞かれるが「no」を選択。 
* 「Check Requiirement」で下記のみ選択して「Execute」を実行し、前提となるライブラリをインストールし「Next」
* High Availabilityは「Standard MySQL Server / Classic MySQL Replication」を選択し「Next」
* Type and Networkingは「Port」を「59306」に変更。「Show Advanced and Logging Option」にチェックを入れて「Next」
* （MySQL8.0以上の場合）Authentication Methodで「Use Legacy Authentication Method」を選択。
* Accounts and Rolesは、下記設定し「Next」
	* Root password     mysqladminpw
	* 「Add User」でユーザID：user、パスワード：passwordのユーザを追加
* Windows Serviceはデフォルトのまま「Next」
* Logging Optionsで下記を設定
	* Error Log      `C:¥workspace¥mysql¥log¥mysql.err.txt`
	* General Log   チェックを入れ、
	`C:¥workspace¥mysql¥log¥mysql.general.txt`に変更
	* 	Slow Query Log    `C:¥workspace¥mysql¥log¥mysql-slow.txt`に変更
* 以後はデフォルトのまま進み「Execute」でインストール
	* MySQLの言語設定がデフォルトはUTF-8のため、Windowsでのデータが扱えるように`C:¥ProgramData¥MySQL¥MySQL Server x.x¥my.ini`のサーバの言語設定を`latin1`から`utf8mb4`に変更するため、`character_set_server= utf8mb4 `を追記して保存する。（隠しフォルダであるため、Windowsボタン＋Rなどで`C:¥ProgramData¥MySQL¥MySQL Server x.x`を入力して開く）
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

* 「スタート」の横の検索窓で「task」と入力し「タスクマネージャー」を起動。「MySQLx.x」を選び右クリックで「再起動」をクリック。「状態」が「実行中」に変わるまで待つ。
* 「スタート」の横の検索窓で「MySQL」と入力し「MySQLx.x Comand Line Client」を起動。インストール時に設定したRoot passwordでログイン。
* mysqlのコマンドラインになったら`create database flower_db;`を実行しデータベースを作成。
* `GRANT ALL ON flower_db.* TO user;`でユーザー`user`に全権限を付与。

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
$ mysql -u root -P 59306-p
Enter password: (mysqladminpw)
mysql>SELECT user, host, plugin from mysql.user
(ここでuser@%の認証方式pluginがmysql_native_passwordか確認)
mysql> ALTER USER ‘user’@‘%’ IDENTIFIED WITH mysql_native_password BY ‘password’;
mysql> FLUSH PRIVILEGES;
mysql> quit

```
	.
### Gitクライアントのインストール

1. Git Windows版のインストール
	* [Git for Windows](https://gitforwindows.org/)より「ダウンロード」をクリックし、インストーラを起動。
2. Gitクライアントの初期設定
	* インストールによるgit configの設定が誤っているケースがあるため、`git config --list`で`color.ui=aute`となっている場合、git cloneなどのコマンドが失敗するため修正する。（正しく`color.ui=auto`となっており`color.ui=aute`が表示されない場合は本手順不要。）
```
git config --global color.ui auto
```

### Visual Studio Codeのインストール

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

#OpenFastPath/Environment/Fixed
