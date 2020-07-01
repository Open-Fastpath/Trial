# OpenFastPathトライアル環境構築WSL2版
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンが64bit BIOS仮想化可能でWindows Update 2004 May 以上が適用可能な場合、トライアル環境用としてWSL2(Windows Subsystem for Linux)上にUbuntuをセットアップする。

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. WSL2・UbuntuによるLinux環境の準備 ＜50分＞
2. トライアル環境の自動構築＜20分＞
	* Ubuntuパッケージリストの更新
	* Ubuntuインストール済みパッケージのアップグレード
	* Ubuntu日本語化
	* JDK インストール
	* Gradle インストール
	* Graphviz インストール
	* Docker インストール
	* 表示設定の変更
	* 再起動
	* Java バージョン確認
	* Gradleバージョン確認
	* Dockerサービス起動
	* Dockerサーバ・クライアント稼働確認
	* MySQLデータベースサーバコンテナ生成・起動
3. Docker クライアントのインストール ＜5分＞
4. Visual Studio Codeのインストールと設定＜10分＞

### WSL2・UbuntuによるLinux環境の準備

Windowsをトレーニング環境とする場合はWindows上でLinuxを動かすことができるWindows Subsystem for Linux（WSL）を利用しLinuxの種類であるUbuntuを動作できるようにする。

1. WSL2を前提としたWindowsバージョンの確認とWindows Update

* Windowsボタン＋Rでファイル名を指定して実行を開き、`winver`コマンドを実行。Windowsのビルドバージョンがバージョン2004（19624）以上であることを確認する。
* バージョンが2004未満の場合は[Windows 10 のダウンロード](https://www.microsoft.com/ja-jp/software-download/windows10)にて「今すぐアップデート」をクリックし最新に更新する。

```
-- Tips Windows Terminalの利用 --

Power Shellの色などのカスタマイズが難しい場合、最近正式リリースされたWindows Terminalをインストールすると、可読性や効率が上がる可能性があり試していただきたい。Power Shell、WSL、コマンドプロンプト、Azureの切り替えができ、タブで複数起動も可能になっている。利用する場合、後続のPowerShellあるいはUbuntuのターミナルという記載はWindows Terminalと置き換えていただいて問題ない。
（導入手順）
* スタート」ボタン横の検索ボックスに”store”と入力し、「Microsoft Store」を選び起動。
* Microsoft Storeの検索のボックスで”Terminal”と入力し検索。 「Windows Terminal」 をインストールする。インストール後「起動」ボタンを押下し、タスクバーでアイコンを右クリック「タスクバーにピン留めする」
* 以後、本文で「ターミナル」と記載しているものは「Windows Terminal」を利用する。
```

2. WSL2の有効化

* * WSL2のメモリ使用量を制限するためWindowsの`C:¥Users¥ユーザ名¥.wslconfig`をなければ作成し下記を記載して保存。（メモ帳などで編集し保存する。）
```
[wsl2]
memory=1GB
swap=0
```
	
* 「スタート」→「-設定」→「アプリ」を開き左メニューより「アプリと機能」をクリック。
* 「関連設定」の「プログラムと機能」をクリック。
* 左メニューにある「Windows機能の有効化または無効化」をクリック。
* 「Linux用Windowsサブシステム」と「仮想マシンプラットフォーム」にチェックを入れてOKをクリックしインストールの上、システムを再起動。
* 「スタート」ボタン横の検索ボックスに"power shell"と入力。Power shellを「管理者として実行」をクリックして起動する。
* 日本語が文字化けする場合は画面左上のアイコンをクリックし「プロパティ」→「フォントタブ」でBIZ ゴシックなど日本語対応しているフォントを選択する。）
* 今後インストールするLinuxはすべてWSL2で実行するように変更する。※ versionと2の間は半角スペース開ける。
	`wsl --set-default-version 2` 
	
3. Ubuntuのインストール

* 「スタート」ボタン横の検索ボックスに”store”と入力し、「Microsoft Store」を選び起動。
* Microsoft Storeの検索のボックスで”Ubuntu”と入力し検索。
* 「Ubntu20.04 LTS」 をインストールし再起動。
* 「複数端末で利用するか」は「いいえ」を選択。
	
4. Ubuntuの起動

* 「スタート」→ 「Ubntu20.04 LTS」で右クリック→「その他」から「管理者をして実行」をクリックし起動。（セットアップ中、Ubuntuは以後必ず管理者権限で起動する。以後ターミナルと表記する。）
* 起動中にユーザIDとパスワードが要求されるためUbuntu用の管理ユーザーとパスワードをセットする。（ubadmin/ubadminpw）
* Ubuntuターミナルの左上アイコンをクリックし「プロパティ」をクリック。プロパティで「Ctrl+Shift+C/Vをコピー/貼り付けとして使用する」をチェックする。（以後ターミナル上でコピーはCtrl＋Shift＋C、貼り付けはCtrl+Shift+Vを利用できる。）フォントタブで「BIZ UD ゴシック」を選択し「OK」をクリック。
* Power Shellのターミナルが終了している場合は、「スタート」ボタン横の検索ボックスに"power shell"と入力。「Power shell」を選び「管理者として実行」をクリックして起動。
* UbuntuがWSL2でインストールされていることを確認する。”Ubntu-20.04” の”VERSION”が”2”と表示されていればよい。
`wsl -l -v` 

```
-- Tips WSL2 Ubuntuの初期化方法 --
Power Shellなどで下記のコマンドを実行すると初期化できる。
または「アプリと機能」で「Ubuntu 20.04 LTS」をクリックし「詳細オプション」をクリックし「リセット」ボタンを押すことで設定を削除する。

wsl --unregister Ubuntu-20.04
```
	
### トライアル環境の自動構築

1. rootパスワードの変更

* ターミナルを起動する。
*  rootユーザのパスワード設定。※以後sudo実行時にパスワードが求められたらrootユーザに設定したパスワードを入力。
```
sudo passwd root
(新しいUNIXパスワードを入力してください)ubadminpw
(新しいUNIXパスワードを再入力してください)ubadminpw
```

2. トレーニングコンテンツのダウンロード

* Open Fast PathのGitからトレーニングコンテンツをダウンロード
```
cd
git clone https://github.com/Open-Fastpath/Trial.git
```

3. 環境の自動構築

* Linux環境のセットアップを自動で実行する。起動時とDockerインストール後のユーザグループ追加時にsudoのパスワードが求められたら設定したsudoのパスワードを入力し続行する。Dockerインストール中にGRUBのセットアップ先の確認画面が表示されるが何も選択せずにTabキーで「Ok」に移動。Enterキーを押し、継続するか確認されたら「Yes」を矢印キーで選択しEnterキーを押して続行する。
	* パッケージリストの更新
	* インストール済みパッケージのアップグレード
	* 日本語化
	* JDK インストール
	* Gradle インストール
	* Graphviz インストール
	* Docker インストール
	* 表示設定の変更
	* 再起動（WSL2ではシェル上のshutdownができないため次の手動手順実行）
```
cd ~/Trial/CourseA/Java/flowershop-trial-env
bash autosetup.sh
```

4. WSL2サービスの再起動

* WSL2の場合、shutdown / rebootは利用できないため、PowerShellを起動し、`wsl -l`で`Ubuntu-20.04`などのディストリビューション名を取得し `wsl -t  Ubuntu-20.04`でWSL2のサービスを再起動する。（または、Windowsを再起動しても良い。）

5. 再起動後の確認とDockerサービスの生成と起動

* 再起動後、ターミナルで接続
* 再起動後のインストール確認とDockerサービスの自動構築を実行する。「Dockerサービス起動」時にsudoのパスワードが求められたら設定したsudoのパスワードを入力し続行する。
	* Java バージョン確認
	* Gradleバージョン確認
	* Dockerサービス起動
	* Dockerサーバ・クライアント稼働確認
	* MySQLデータベースサーバコンテナ生成・起動
```
cd ~/Trial/CourseA/Java/flowershop-trial-env
bash autosetup_afterreboot.sh
```

### Docker クライアントのインストール

* Docker HubのサイトにてDocker Hubのユーザーを作成し、Docker for Windowsをダウンロードし何も設定を変更しないでインストール。※インストーラの起動に時間がかかるが待つこと。
[Docker Hub](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)
* インストール中に「Configration」の指定が求められるが「Enable WSL 2 Windows Features」にチェックがされている状態のまま「OK」ボタンをクリックすること。（この設定でWSL2上のコンテナにアクセスできる。）
* インストールが完了するとWindowsからサインアウトするか確認されるが、Windows自体を再起動する。
* 再起動後、ターミナルを起動しUbuntuのDockerサービスを起動しDockerのサーバとクライアントが両方起動することを確認。（トライアル環境ではDocker,をデーモンで起動していないためWindowsやWSLの再起動時にはサービスの起動が必要。）
```
sudo service docker start
docker version
```
* 	Docker for Winsowsを起動。通知領域にDockerのアイコンが表示されるまで時間がかかるが待つこと。(WSL2の起動とDocker Serverの起動が遅い場合にClientからの実行エラーとなることがあるがその場合は右下の通知領域にDockerのアイコンを右クリックしRestartをする。)
```
ー解説ー
WSL2上のDocker EngineにWindowsからDocker Cientとしてアクセスする。Visual Studio CodeはDocker Desktopのdocker Clientを通じてWSL2上のDockerコンテナにアクセス可能となる。
```


```
ー　Tips cgroupがマウントできないエラーが発生したらー
”cgroups: cannot find cgroup mount destination”と出ている場合には
cgroupsのマウント先がない可能性がある
sudo ls -ltr /sys/fs/cgroup/systemd
を実行しフォルダが存在する場合、Windowsを再起動してもう一度試す。

存在しない場合はフォルダを作成しマウントする。
sudo mkdir /sys/fs/cgroup/systemd
sudo mount -t cgroup -o none,name=systemd cgroup /sys/fs/cgroup/systemd 
対応ぼUbuntuを終了しの後Windowsを再起動する。
```

### Visual Studio Codeのインストールと設定

1. Visual Studio Codeのインストール

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
* 検索結果よりJapanese Language Pack for VS Codeを選択しインストールし、VS Codeを再起動する。

3. リモート接続環境のインストール

* 「拡張機能」にて検索窓に”remote”を入力し、検索結果よりRemote Developmentを選択しインストール。
* 「アクティブバー」から「リモートエクスプローラー」を選び、リモートエクスプローラの右横にあるコンボボックスにて`WSL Targets`を選択。「TARGETS(WSL)」の下にWSL2のUbuntuが表示される。右にあるフォルダボタン「Connect to Host in New Windows」をクリックし、新たに起動したVS Codeの画面のメニューから「表示」→「ターミナル」を選択。ターミナル右上の上矢印ボタンを押下して全画面表示にし操作する。

4. Docker for VS Codeのインストールと設定

* （Ubuntuへのリモート接続で起動したVS CodeではなくWindows上で動作しているVS Codeにて）「拡張機能」の検索窓で「docker」を入力、「Docker for Visual Studio Code」をインストールする。
* 「アクティブバー」から「Docker」を選択。CONTAINERSにDockerコンテナが表示され、コンテナごとの起動・停止が可能であること。（コンテナ名称横の再生・ストップそれぞれアイコンで可能）

5. SQLToolsのインストールと設定

* （Ubuntuへのリモート接続で起動したVS CodeではなくWindows上で動作しているVS Codeにて） 「アクティブバー」から「Docker」を選択し、「flower_db」のコンテナが緑再生ボタンの稼働中になっているか確認。稼働中でない場合は右クリックしてStartを選択。 	
* 「拡張機能」の検索窓で”sqltool”と入力。	 検索結果からSQLToolsを選択しインストール。インストール後「再読み込みが必要です」をクリックしVS Codeを再起動。
* 「アクティブバー」から「拡張機能」を選択し、検索窓に”sqltool”と入力し、「アクティブバー」から「SQLTools MySQL/Maria DB」を選択肢インストール。インストール後「再読み込みが必要です」をクリックしVS Codeを再起動。
* 「アクティブバー」からSQLTool を選択。「CONNECTIONS」から「Add New Connection」のアイコンをクリックしMySQLを選択。（アクティブバーにSQLToolsが表示されない場合はコマンドパレット`Shift＋Ctrl/Command＋p`から「sql add」などと検索し「SQLTools Management : Add New Connection」を選択しても良い。）
* データベースの選択でMySQLを選び下記項目を設定。
	* Connection Name     vs_con_flower_db
	* Server Address         localhost
	* Port                            59306
	* Database                    flower_db
	* Username                   user
	* Use password            Save password
	* Password                    password
* 「TEST CONNECTION」を実行して「SUCCESS…」と表示されたら「SAVE CONNECTION」をクリック。
* 「CONNECTIONS」に追加された「vs_con_flower_db」をクリックし接続されることを確認。
* 「コマンドパレット」を開き”sqltools co”と入力。”SQLTools Connection: Run Query”を実行し`show databases;`を実行。flower_dbが表示されることを確認。
* 右下の「管理」→「設定」を選択し、検索窓に「sqltool」を入力。「SQLTools Setting…」を選び、「Sqltools: Auto Open Session Files」のチェックを外す。（これにより不要なデータベース接続された新規ファイルが作成されなくなる。）

6. Java関連基本拡張機能のインストール

* 「アクティブバー」から「リモートエクスプローラー」を選び、リモートエクスプローラの右横にあるコンボボックスにて`WSL Targets`を選択。「TARGETS(WSL)」の下にWSL2のUbuntuが表示される。右にあるフォルダボタン「Connect to Host in New Windows」をクリックし、新たに起動したVS Codeの画面のメニューから「表示」→「ターミナル」を選択。ターミナル右上の上矢印ボタンを押下して全画面表示にし操作する。
* 「拡張機能」を選択し 検索窓に下記インストール対象の名称の一部を入力。検索してインストール。指示に従い「再読み込み」を実施。
	* Java Extension Pack
	* Spring Boot Extension Pack
	* Lombok Annotations Support for VS Code

#OpenFastPath/CourseA/Java/Trial