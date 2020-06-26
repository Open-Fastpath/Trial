# OpenFastPath環境構築Ubuntu版
ー　 version 2020.06.25　ー

## 本手順の前提
* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンが64bit BIOS仮想化可能で、WS2、Hyper-V、VMware、Raspberry PIいずれかにてUbuntuをインストールした後に本手順を用いてトレーニング環境を構築する。（Raspberry PIは一部インストール方法が異なるため＊for Raspberrry PI＊のコラムを参照）

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. Ubuntuの初期設定＜10分＞
2. Dockerによる仮想サーバ環境の準備＜5分＞
3. Docker for Windows インストール（WSL2版のみ実施）＜5分＞
4. Open JDKのインストール＜5分＞
5. Gradleのインストール＜3分＞
6. Graphvizのインストール＜2分＞
7. Visual Studio Codeのインストール＜5分＞
8. トレーニングコンテンツの配置＜5分＞

### Ubuntuの初期設定
[想定所要時間 10分]

Ubuntuのターミナル、またはsshで接続したターミナルで実施。

1. パッケージの最新化

*  rootユーザのパスワード設定。※以後sudo実行時にパスワードが求められたらrootユーザに設定したパスワードを入力。
```
sudo passwd root
(新しいUNIXパスワードを入力してください)ubadminpw
(新しいUNIXパスワードを再入力してください)ubadminpw
```

* インストール可能なパッケージの一覧を更新。
```
sudo apt update
```

* インストール済のパッケージを新しいバージョンにアップグレード。
```
sudo apt upgrade -y
```

2. Ubuntuの日本語化

* 日本語パックをインストールし言語設定を切替。
```
sudo apt -y install language-pack-ja
sudo update-locale LANG=ja_JP.UTF8
```

* Ubuntuのマニュアル日本語版をインストール。コマンドは英語のままが良い方は`sudo update-locale LANG=en_US.UTF8`で戻せる。
```
sudo apt -y install manpages-ja manpages-ja-dev
```	

3. ネットワークツールのインストール
	
* ネットワークツールを`sudo apt install net-tools`でインストール
* `netstat -tln`でOSが公開しているポートを確認。
* `networkctl status -a`でネットワークの設定を確認。（`:q`で終了できる。）

```
-- Tips Ubuntu 20.04 LTSからのネットワーク設定ーー
Ubuntuは20.04 LTSからネットワーク設定の方式がLinux共通のYAMLで記載するnetplan方式に変わっている。cloudinitなどのネットワークフレームワークなどで自動生成されたりすることがあり、netplanは/etc/netplan配下のYAMLを全て辞書順に適用し上書きする方式をとっているため、インストール時の/etc/00-installer-config.yamlを上書きしても反映されないことがある。したがってマニュアル設定をする場合は最後に上書き適用されることを想定し99を先頭にして上書きする設定ファイルを作成することが望ましい。

（DNSのIPアドレスを固定IPにするネットワークの設定変更の例）
※インデントは精緻にチェックされるため間違えると正しく認識されない
-------
network:
  version: 2
  renderer: networkd
  ethernets:
    enp0s8:
      nameservers:
        addresses: [192.168.xx.1]
-------
保存してからnetplanのサービス変更を実行して反映する。
sudo netplan apply
反映結果をネットワークツールで反映されていることを確認する。
networkctl status -a
```

4. SSHポート変更
 
* SSHポートをセキュリティのためデフォルトの22から変更する。`sudo vi /etc/ssh/sshd_config`で`Port`と`AllowTcpForwarding `を変更。下記は例として59001としているが49152–65535の範囲で未使用のポート番号を選定する。（`sudo lsof -i -P | grep "LISTEN"`または`netstat -t`で利用中のポートを確認する。）
```
#ssh USERNAME@192.168.XX.XX -p 59001でログイン可能にする
#Port 22 
Port 59001 #(値は49152–65535の範囲で任意に設定)
(中略)
AllowTcpForwarding yes　※検索してコメントを外す
```
* sshdを再起動`sudo systemctl restart sshd`
* SSH接続してセットアップしている場合、一度ログオフをしてから以後次の様にポートを指定して接続する。
```
ssh uabmin @192.168.XX.XX -p 59001
(パスワード) ubadminpw
```

5. ファイアウォールの設定

* ファイアウォールの状態を確認`sudo ufw status verbose`
* ファイアウォールのポートを許可した上で反映させ有効化。sshは同一のネットワークグループからのみアクセスを許可（SSHは5.で変更したポート番号を設定。Dockerはこの後の手順で割り当てるが事前に割り当て設定。ポート番号はあくまで例でありSSHのポート番号と同様未使用ポート番号を割り当てる。）
```
（Webサービスアクセス許可）
sudo ufw allow 80
sudo ufw allow 8080
sudo ufw allow 8090
sudo ufw allow 9100
sudo ufw allow 443
（SSHアクセス許可）
sudo ufw allow from 192.168.XX.0/24 to any port 59001
（Dockerアクセス許可）
sudo ufw allow from 192.168.XX.0/24 to any port 59998
（データベースアクセス許可 MySQLは3306から59306にDockerで変更指定）
sudo ufw allow from 192.168.XX.0/24 to any port 59306
sudo ufw allow from 127.0.0.1 port 59306
（設定を再読み込み）
sudo ufw reload
（ファイアウォール有効化）
sudo ufw enable
```

* 設定を誤った場合は下記にてルールを削除し、再度ルールを追加する。
```
sudo ufw status numbered
sudo ufw delete numberedで表示される左の番号
```

```
-- Tips ユーザ管理とセキュリティ強化 --
Linuxのデフォルトユーザは極力リモートログインできないように制御し、root権限もいくつかのユーザグループに実行権限を委譲する設定をすることがセキュリティ上望ましい。

＊＊for Raspberry PI＊＊
Raspberry PIのように物理的に独立しインターネットに接続している機器（WindowsやMacは一定のセキュリティが確保されていること前提）では必ず実行することが望ましい。

* 開発グループと運用グループと管理グループを追加
sudo groupadd developer
sudo groupadd opelrator
sudo groupadd manager
sudo adduser devuser		※パスワードuserdev
sudo adduser opuser		※パスワードuserop
sudo adduser mgruser		※パスワードusermgr
sudo usermod -aG developer devuser
sudo usermod -aG opelrator opuser
sudo usermod -aG manager mgruser
	
* ルート権限を運用グループと管理グループに`sudo visudo`で設定を開き分割指定するように編集。
-------------
Cmnd_Alias USERMGR = /usr/sbin/adduser, /usr/sbin/useradd, /usr/sbin/newusers, \
/usr/sbin/deluser, /usr/sbin/userdel, /usr/sbin/usermod, /usr/bin/passwd 
Cmnd_Alias SHUTDOWN = /sbin/halt, /sbin/shutdown, \
/sbin/poweroff, /sbin/reboot, /sbin/init, /bin/systemctl 

%operator    ALL=(ALL:ALL) SHUTDOWN
%manager    ALL=(ALL:ALL) USERMGR

ubadmin       ALL=(ALL:ALL) ALL
-------------

* rootユーザとubuntuユーザロック（ubuntuユーザはisoでインストールする際のデフォルトユーザ）

sudo passwd -d ubuntu
sudo passwd -l ubuntu
sudo passwd -l root
```

6. SSH認証キー設定

* SSH接続をするクライアントのPCのターミナルで`ssh-keygen`を実行しSSHに必要な秘密鍵と公開鍵のペアを生成。保管ファイルとパスフレーズが問われるが変更せずにリターンキー押下。（Windowsは`C:¥Users¥ユーザ名¥.ssh`の配下に、Macは/Users/ユーザ名/.ssh配下に生成される）
* ターミナルでUbuntu Serverに接続する。`ssh ubadmin@接続先IPアドレス -p 59001`鍵交換をしていないため、認証が確立されていないが続けるか確認される。yesを入力しubadminのパスワードを入力してログイン。
* ログインしたUbuntuでsshの公開鍵格納用のフォルダを作成。`mkdir -p ~/.ssh ; chmod 700 ~/.ssh`
* でUbuntuへのSSHからログアウト。
* 戻ったターミナルlでUbuntu上に作成したフォルダにPCで作成した公開鍵をセキュアコピーで転送。ubadminのパスワードが聞かれるため入力。（下記コマンドの`192.168.X.X`はUbuntuのIPアドレスに変更する）
```
cd ~/.ssh
scp .¥id_rsa.pub ubadmin@192.168.X.X:~/.ssh/authorized_keys
```

* 再度、ターミナルでSSHでUbuntu Serverに接続する。`ssh ubadmin@192.168.X.X -p 59001`でパスワードなしで鍵認証でログインできることを確認し、`exit`でログアウト。（コマンド中の`192.168.X.X`は接続先サーバのIPアドレスに変更する。）

### Dockerのインストール
[想定所要時間 5分]

* Ubuntuのターミナルまたはsshで接続したターミナルで実行。
* Dockerインストールに必要な準備をする。（※本手順はRaspberryPIでは不要）
	* インストールに必要なツールのインストール。
	* Dockerの取得に必要なGPGキーの追加。
	* パッケージの取得元としてdockerの安定版があるサイトを追加。
	* 追加したパッケージ取得元をもとに取得可能パッケージを更新。
```
cd 
bash linux-settings/docker-setup/set-docker-env.sh
```

* Dockerをインストール。
```
sudo apt install docker-ce docker-ce-cli containerd.io docker-compose -y
```

```
＊＊for Raspberry PI＊＊
-- Tips Raspberry PI AMD系CPUにおけるDockerのインストール --
Raspberry PIはCPUがAMDの系列であり、WindowsやMacのARMアーキテクチャのCPUと異なる。RaspberryPI 4 4GBではAMD64bitに合わせる必要がある。
AMD64bitではUbuntu標準のパッケージでdockerが動作するため、Docker社のUbuntuパッケージではなくUbuntu標準のDockerをインストールする。
「Dockerインストールに必要な準備をする。」ではARM系のCPUアーキテクチャにあったDocker社のDockerをインストールするようにパッケージリポジトリを切り替えている。

（RaspberryPI AMD64の場合のインストールコマンド）
sudo apt install —y docker.io docker-compose
```

* Dockerの実行ユーザを現在のユーザーに変更し、通常ユーザでDockerが使用できるように設定の上、`sudo reboot`コマンドでUbuntuを再起動。（WSLの場合はexitし再度管理者権限で起動する。）
```
sudo usermod -aG docker $USER
sudo gpasswd -a $USER docker
```

* Ubuntu Serverのターミナル側で再起動する。（WSL2の場合、shutdown / rebootは利用できないため、PowerShellより`wsl -l`で`Ubuntu-20.04`などのディストリビューション名を取得し `wsl -t  Ubuntu-20.04`でwslのサービスが再起動される）
```
sudo reboot
```

* Ubuntu Serverが再起動してきたらログイン（WSLの場合は再度ターミナルを起動）し、docker serverをサービスとして起動する。
```
sudo service docker start
```

* `docker version`にてClientとServer両方が表示されることを確認。

* エディタをデフォルトのnanoからviへ変更する。（機種により選択肢が異なるがvim.basicの番号を選択する。）
```
sudo update-alternatives --config editor

alternative editor (/usr/bin/editor を提供) には 5 個の選択肢があります。

  選択肢    パス              優先度  状態
------------------------------------------------------------
* 0            /bin/nano            40        自動モード
  1            /bin/ed             -100       手動モード
  2            /bin/nano            40        手動モード
  3            /usr/bin/code-oss    0         手動モード
  4            /usr/bin/vim.basic   30        手動モード
  5            /usr/bin/vim.tiny    15        手動モード

現在の選択 [*] を保持するには <Enter>、さもなければ選択肢の番号のキーを押してください: 4
```

* リモートからdockerデーモンへのアクセスを許可するために編集する。ただしDocker Service デフォルトのポート2375はセキュリティのため利用しない。`sudo systemctl edit docker.service`を実行すると空の設定ファイルが表示されるため、下記を貼り付け保存する。
```
[Service]
ExecStart=
ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:59998 --containerd=/run/containerd/containerd.sock
```

* 編集後にデーモンとサービスを再起動
```
sudo systemctl daemon-reload
sudo systemctl restart docker.service
```

* Ubuntu上でdockerが実行できるか確認
`docker -H tcp://192.168.XX.XX:59998 run --rm hello-world ` 

### Docker for Windows インストール（WSL2版のみ実施）
[想定所要時間 5分]

* Docker HubのサイトにてDocker Hubのユーザーを作成し、Docker for Windowsをダウンロードし何も設定を変更しないでインストール。※インストーラの起動に時間がかかるが待つこと。
[Docker Hub](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)
* インストール中に「Configration」の指定が求められるが「Enable WSL 2 Windows Features」にチェックがされている状態のまま「OK」ボタンをクリックすること。（この設定でWSL2上のコンテナにアクセスできる。）
* インストールが完了するとWindowsからサインアウトされるので再度サインインする。
* 	Docker for Winsowsを起動。※通知領域にDockerのアイコンが表示されるまで時間がかかるが待つこと。
```
ー解説ー
Docker for Windows はバージョン2.2.2.0以降WSL2 上のDockerを参照できるようになった。WSL2上のDocker EngineにWindowsからDocker Cientとしてアクセスできる。Visual Studio CodeはDocker Desktopのdocker Clientを通じてWSL2上のDockerコンテナにアクセスできる。
５月時点のバージョンは2.3.0.2をインストールする。
```

### Open JDKのインストール
[想定所要時間 5分]

Visual Studio CodeからUbuntu上で開発しJavaの入っているコンテナへ今後のトレーニングでリリースをするため、ホストOS上にもOpenJDKをインストールする。以下はUbuntuターミナルまたはsshで接続したターミナルで実施。

* OpenJDK11をインストール。
```
sudo apt install -y openjdk-11-jdk
```

```
Ubuntuの上記ケースでは自動的にJAVA_HOMEの環境変数は設定されないため、Githubより取得したUbuntu設定用の~/.bashrcにJAVA_HOMEの設定をあらかじめ記載している
Ubuntu向けのAdoptOpenJDK11 with HotSpotがまだ安定していないためOracleが支援している無償提供版OpenJDK11を入れている（いずれもOpenJDK仕様に準拠）
RaspberryPIはCPUのアーキテクチャがAMD系であるが本インストールで導入されるARM64向けのJDKでも動作する。
```

* インストール後、JAVA＿HOMEの環境変数で指定されているパスにOpenJDK11がインストールされたことを確認。
```
echo $JAVA_HOME
$JAVA_HOME/bin/java -version
```

```
ーJAVAインストールパスの確認ー
Javaのインストールパスは本トレーニングでは下記に設定している。
echo $JAVA_HOMEの結果が下記ではない場合問い合わせのこと。
Ubuntu	"/usr/lib/jvm/java-11-openjdk-amd64"
Mac		"/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home"
```

### Gradleのインストール

* 標準ではGradleのバージョンが2.xもしくは4.xと古くなるため、パッケージを最新の取得元に変更してインストールする。バージョンが6以上であれば良い。
```
sudo add-apt-repository ppa:cwchien/gradle
(途中でEnterキーを押下)
sudo apt-get update
sudo apt -y install gradle
gradle -v
```

### Graphvizのインストール

* VS CodeでMarkdown上でPlantUMLを有効にするためにGraphvizのライブラリをインストールする。
```
sudo apt -y install graphviz
```

### Visual Studio Codeのインストール

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

```
＊＊for Raspbarry PI＊＊
-- Tips Raspberry PI　LubuntuデスクトップへのVS Codeインストール --
* Raspberry PIのUbuntu用はOSSのVS Codeコミュニティ版を[Visual Studio Code for Chromebooks and Raspberry Pi](https://code.headmelted.com/)の手順に従いインストールする。ssh接続かLubuntuのLX TerminalまたはQ Terminalで実行。
----------------------------
sudo -s
. <( wget -O - https://code.headmelted.com/installers/apt.sh )
exit
sudo reboot
----------------------------

＊再起動後Lubuntuにログインし、メニュー→「プログラミング」→「Code-OSS（headmelted）」を選択し、VS Code起動。
```

### トレーニングコンテンツの配置

1. トレーニングコンテンツのダウンロード

* Open Fast PathのGitからトレーニングコンテンツをダウンロード
```
cd
git clone https://github.com/Open-Fastpath/Trial.git
```

2. トレーニング環境の配置

* UbuntuのターミナルでHomeディレクトリに移動しワークスペースとなるディレクトリ作成。
```
cd
mkdir workspace
```

* 次世代開発Java版トレーニング環境をGitよりダウンロードしたファイルより配置。
```
cd ~/workspace
cp -rp ~/Trial/CourseA/Java/flowershop .
```

```
＊＊for Raspberry PI＊＊
-- RaspberryPI用のファイル・設定の反映 --
Githubのトレーニングキットでは、Raspberry PI様にカスタマイズされた下記の設定ファイルをforRaspberryPIフォルダの下に揃えている。これらをコピーすることで以後、他のUbuntu環境と同様に動作できる。
* gradleのビルド依存関係（build.grade）
* MariaDB用接続設定（application.properties）
* データベースコンテナのMariaDBへのイメージ変更（docker-compose_flowerapps.yml ）
* アプリケーションサーバのJavaイメージ変更（Dockerfile）
* MariaDB用初期設定ファイル（flower_mysql.env）
---------
cd ~/workspace/flowershop/forRaspberryPI
bash copy_forRaspberryPI.sh
---------
```

2. Ubuntuの表示設定をカスタマイズ

* ログインユーザのHomeディレクトリの下にGitから取得したフォルダの配下にある設定ファイルをHomeディレクトリに反映
	* Ubuntuのターミナル表示色
	* Vimエディタの表示色・ファイル別識別
	* Java動作パス(JAVA_HOME)
```
cd ~/Trial/CourseA/Java
cp -p linux-settings/.bashrc .
cp -rp linux-settings/.vim* .
cp -p linux-settings/.colorrc .	
source .bashrc
```
		
```
-- Tips Linux系のログインユーザ別設定 --
Linux系のOSではログインユーザーごとにHomeディレクトリが割り当てられ、Homeディレクトリに設定が保持される。多くは.から始まるファイルになっており、シェルの環境やソフトウエアによりログイン時に適用されるファイルが異なる。bash系のシェル環境では.bashrc zsh系の環境では.zshrcや.zprofileなどが起動時に適用される。

.から始まるファイルは通常の`ls`コマンドでは見えず`ls -a`などaのオプションを加えることで見ることができる。
```

#OpenFastPath/Environment/Fixed#
