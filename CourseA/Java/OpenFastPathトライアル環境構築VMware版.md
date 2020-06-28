# OpenFastPathトライアル環境構築VMware版
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンが64bit BIOS仮想化可能な場合、トライアル環境用としてVMware Workstation Player上にUbuntuをセットアップする。（トレーニング本番では一つずつ設定するが、トライアル環境では自動設定するためすぐにクリーンナップできるVMwareを利用する）

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. VMware・UbuntuによるLinux環境の準備 ＜50分＞
2. トライアル環境の自動構築＜20分＞
	* Ubuntuパッケージリストの更新
	* Ubuntuインストール済みパッケージのアップグレード
	* Ubuntu日本語化
	* JDK インストール
	* Gradle インストール
	* Graphviz インストール
	* Docker インストール
	* 表示設定の変更
	* Ubuntu再起動
	* Java バージョン確認
	* Gradleバージョン確認
	* Dockerサービス起動
	* Dockerサーバ・クライアント稼働確認
	* MySQLデータベースサーバコンテナ生成・起動
3. Visual Studio Codeのインストールと設定＜10分＞

### VMware・UbuntuによるLinux環境の準備
[想定所要時間 50分]

Windows Homeをトレーニング環境とする場合はWindows上でLinuxを動かすことができるように、VMwareをインストールし、仮想化環境の上でLinuxの種類であるUbuntuを動作できるようにする。

1. Ubuntuののダウンロード

* [Download Ubuntu Server | Download | Ubuntu](https://ubuntu.com/download/server)よりUbuntu Server20.04 LTSのisoファイルをダウンロード

2. VMwareのダウンロードとインストール

* [VMware Workstation Player のダウンロード | VMware | JP](https://www.vmware.com/jp/products/workstation-player/workstation-player-evaluation.html)よりVMware Workstation Playerをダウンロード。 インストールフォルダの`C:¥workspace¥vmware`への変更と拡張キーボードのインストールにチェックし、他は全てデフォルトのままインストール。
* VMware Workstation Playerのインストール完了画面で非商用利用であるため「ライセンス」をクリックし「スキップ」を選択、「完了」し、システムを再起動
* VMware Workstation Playerを起動し、「非営利目的で・・・無償で使用する」のまま「続行」を選択、「完了」する。

3. Ubuntuのインストール前設定

* 「新規仮想マシンの作成」をクリック。「インストーラディスクイメージファイル」を選択し1.でダウンロードしたUbuntuのISOファイルを参照し「次へ」。ウィザード中に下記を設定し「完了」
	* ホストOS　　ubuntu2004
	* ユーザ名　　 ubadmin
	* パスワード　 ubadminpw
	* インストール先　`C:\workspace\ubuntu`

4. Ubuntuのインストール
* * メニューにある再生のアイコン（パワーオンボタン）をクリックしUbuntuのインストールを開始。
* 設定のウィザードが始まるため、下記選択しながら先に進む。キーボードの上下とタブキーで移動しリターンキーで選択する。
	* 言語　　　　　　　　　　　「English」を選択
	* Instoller Update Available　  「Continue without updating」選択
	* キーボード　　　　　　　　Japanese
		* TABで移動しリターンで選択肢を表示。上下カーソルでJapaneseを選択し「Done」を選択。　※画面上に見えない場合スクロール
	* ネットワーク
		* ネットワークはVMwareはローカルIPはデフォルトでホストと共通のため、インターネット向けのIPアドレスの身の設定となる。デフォルトのDHCPのままで良い。
	* プロキシ　デフォルトのまま「Done」でリターンキー押下。
	* aptリポジトリ　デフォルトのまま「Done」でリターンキー押下。
	* インストールディスク　　Use an entire diskのまま「Done」でリターンキー押下。
	* インストール先　デフォルトのまま「Done」でリターンキー押下。
	* ディスクフォーマットのConfirmで「Continue」でリターンキー押下。
	* ログイン情報　　下記入力し「Done」でリターンキー押下。
		* 名前　　　　ubadmin
		* サーバ名　　ubuntu2004
		* ユーザID        ubadmin
		* パスワード　 ubadminpw
	* OpenSSHのインストール　VS CodeからOpen SSHでリモート接続するためチェックして「Done」でリターンキー押下。
	* 追加パッケージ　デフォルトのまま「Done」
* インストールが終わるまで待ち（60分くらい）、「Reboot」が表示されたらリターンキー押下。
* `Failed unmounting /cdrom`が表示されるが。`Please remove the installation medium. then press ENTER:`が表示されたらリターンキーを押下。
* 「ubuntu login:」が表示されたら設定したユーザIDとパスワードでログイン
* シャットダウンする。`shutdown now`
	
5. SSHによるアクセス

* VMWareにSSHアクセスできるようにするため、VMware Workstation Playerを起動し、VMwareの仮想マシンのリストからインストールした「Ubuntu2004」を右クリックし「設定」を開く。
* 「ネットワークアダプタ」を選択。「NAT」を選択。
* 左下の「追加」をクリックし、「ハードウェア追加ウィザード」で一覧から「ネットワークアダプタ」を選択し「完了」をクリック。追加された「ネットワークアダプタ２」で「ホストオンリー」を選び「OK」をクリック。
* VMwareのリストから、「Ubuntu2004」を選び、再生ボタンをクリック。
* Ubuntuが起動したらユーザIDとパスワードを入力しログイン後、`ip addr`でUbuntuのIPアドレスを把握する。
* スタート→「Power Shell」と入力しPower Shell のターミナル起動（Windows Terminalでもよい）
* SSH接続をするクライアントのPCのターミナルで`ssh-keygen`を実行しSSHに必要な秘密鍵と公開鍵のペアを生成。保管ファイルとパスフレーズが問われるが変更せずにリターンキー押下。（Windowsは`C:¥Users¥ユーザ名¥.ssh`の配下に、Macは/Users/ユーザ名/.ssh配下に生成される）
*  ターミナルでUbuntu のログインユーザとIPアドレスを指定してSSHでログインする。以後、sshで接続したターミナルで実行する。（下記コマンドの`192.168.X.X`はUbuntuのIPアドレスに変更する。）
```
ssh ubadmin@192.168.X.X
（認証が確立されていないが続けるか確認される。yesを入力しubadminのパスワードを入力してログイン）
```

* ログインしたUbuntuでsshの公開鍵格納用のフォルダを作成。
```
mkdir -p ~/.ssh ; chmod 700 ~/.ssh
```

* `exit`でUbuntuへのSSHからログアウト。
* 戻ったターミナルlでUbuntu上に作成したフォルダにPCで作成した公開鍵をセキュアコピーで転送。ubadminのパスワードが聞かれるため入力。（下記コマンドの`192.168.X.X`はUbuntuのIPアドレスに変更する。）
```
cd ~\.ssh
scp .\id_rsa.pub ubadmin@192.168.89.128:~/.ssh/authorized_keys
```

* 再度、ターミナルでSSHでUbuntu Serverに接続する。`ssh ubadmin@192.168.X.X`でパスワードなしで鍵認証でログインできることを確認し、`exit`でログアウト。（下記コマンドの`192.168.X.X`はUbuntuのIPアドレスに変更する。）
	
### トライアル環境の自動構築

1. rootパスワードの変更

* Power Shellにてsshで接続。
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

* 実際のトレーニングではLinux環境のセットアップも自ら実施するがトライアル環境では自動でセットアップする。途中sudoのパスワードが求められたら設定したsudoのパスワードを入力し続行する。
	* パッケージリストの更新
	* インストール済みパッケージのアップグレード
	* 日本語化
	* JDK インストール
	* Gradle インストール
	* Graphviz インストール
	* Docker インストール
	* 表示設定の変更
	* 再起動
```
cd ~/Trial/CourseA/Java/flowershop-trial-env
bash autosetup.sh
```

4. 再起動後の確認とDockerサービスの生成と起動

* 再起動後、Power Shellで再度ssh接続
* 再起動後のインストール確認とDockerサービスの自動構築を実行する。途中sudoのパスワードが求められたら設定したsudoのパスワードを入力し続行する。
	* Java バージョン確認
	* Gradleバージョン確認
	* Dockerサービス起動
	* Dockerサーバ・クライアント稼働確認
	* MySQLデータベースサーバコンテナ生成・起動
```
cd ~/Trial/CourseA/Java/flowershop-trial-env
bash autosetup_afterreboot.sh
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
* 「アクティブバー」から「リモートエクスプローラー」を選び、リモートエクスプローラの右横にあるコンボボックスにて`SSH Targets`を選択し、 「TARGETS(SSH)」の右にある歯車ボタンをクリック。設定ファイル格納フォルダの選択にてそれぞれの環境のHomeディレクトリ配下の`.ssh/config`フォルダを選択して編集して保存。
```
Host 表示名として任意の名称（Ubuntu2004など）
	HostName 192.168.xx.xx （UbuntuのIPアドレス）
	User ubadmin
	IdentityFile ~/.ssh/id_rsa
```

* リモートエクスプローラの「TARGETS(SSH)」の下に登録された設定ファイルで`Host`に指定した名称が表示される。右にあるフォルダボタン「Connect to Host in New Windows」をクリックし、接続先OSタイプでLinuxを選択。新たに起動したVS Codeの画面のメニューから「表示」→「ターミナル」を選択。ターミナル右上の上矢印ボタンを押下して全画面表示にし操作する。

4. Docker for VS Codeのインストールと設定

* Ubuntuに接続しているVS Code環境で「拡張機能」の検索窓で「docker」を入力、「Docker for Visual Studio Code」をインストールする。
* 「アクティブバー」から「Docker」を選択。CONTAINERSにDockerコンテナが表示され、コンテナごとの起動・停止が可能であること。（コンテナ名称横の再生・ストップそれぞれアイコンで可能）

5. SQLToolsのインストールと設定

* 「アクティブバー」から「Docker」を選択し、「flower_db」のコンテナが緑再生ボタンの稼働中になっているか確認。稼働中でない場合は右クリックしてStartを選択。 	
* 「拡張機能」の検索窓で”sqltool”と入力。	 検索結果からSQLToolsを選択しインストール。インストール後「再読み込みが必要です」をクリックしUbuntuに接続しているVS Codeを再起動。
* 「アクティブバー」から「拡張機能」を選択し、検索窓に”sqltool”と入力し、「アクティブバー」から「SQLTools MySQL/Maria DB」を選択肢インストール。インストール後「再読み込みが必要です」をクリックしUbuntuに接続しているVS Codeを再起動。
* 「アクティブバー」からSQLTool を選択。「CONNECTIONS」から「Add New Connection」のアイコンをクリックしMySQLを選択。（アクティブバーにSQLToolsが表示されない場合はコマンドパレット`Shift＋Ctrl/Command＋p`から「sql add」などと検索し「SQLTools Management : Add New Connection」を選択しても良い。）
* データベースの選択でMySQLを選び下記項目を設定。
	* Connection Name     vs_con_flower_db
	* Server Address         localhost
	* Port                            3306
	* Database                    flower_db
	* Username                   user
	* Use password            Save password
	* Password                    password
* 「TEST CONNECTION」を実行して「SUCCESS…」と表示されたら「SAVE CONNECTION」をクリック。
* 「CONNECTIONS」に追加された「vs_con_flower_db」をクリックし接続されることを確認。
* 「コマンドパレット」を開き”sqltools co”と入力。”SQLTools Connection: Run Query”を実行し`show databases;`を実行。flower_dbが表示されることを確認。

6. Java関連基本拡張機能のインストール

* リモートエクスプローラー右横のコンボボックスから「SSH Targets」を選び、登録されたホスト名`ubuntu2004`の右にあるフォルダボタン「Connect to Host in New Windows」をクリックし、接続先OSタイプでLinuxを選択。Ubuntuに接続したVS Codeを新たに開く。
* 「アクティブバー」より「リモートエクスプローラー」を選択。
* 「拡張機能」を選択し 検索窓に下記インストール対象の名称の一部を入力。検索してインストール。指示に従い「再読み込み」を実施。
	* Java Extension Pack
	* Spring Boot Extension Pack
	* Lombok Annotations Support for VS Code

#OpenFastPath/Environment/Trial