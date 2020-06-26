# OpenFastPath環境構築手順Hyper-Vセットアップ
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンが64bit BIOS仮想化可能で、 Windows Update 2004 May 28以上が適用不可であり、Windows Pro などでHyper-Vが利用可能な場合（Windowsボタン+Rで"systeminfo"と入力し、Hyper-Vの要件がすべて「はい」である場合）に本手順を用いてHyper-V上にUbuntuをセットアップする。
* * セットアップ後は「OpenFastPath環境構築Ubuntu共通」にてトレーニング環境を構築する。

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. Hyper-V・UbuntuによるLinux環境の準備 ＜20分＞

### Hyper-V・UbuntuによるLinux環境の準備
[想定所要時間 20分]

Windowsをトレーニング環境とする場合はWindows上でLinuxを動かすことができるように、 Hyper=Vをオンにし、仮想化環境の上でLinuxの種類であるUbuntuを動作できるようにする。

1. Ubuntuののダウンロード

* [Download Ubuntu Server | Download | Ubuntu](https://ubuntu.com/download/server)よりUbuntu Server20.04 LTSのisoファイルをダウンロード

2. Hyper-Vの有効化

* 	「スタート」→「-設定」→「アプリ」を開き左メニューより「アプリと機能」をクリック。
* 「関連設定」の「プログラムと機能」をクリック。
* 左メニューにある「Windows機能の有効化または無効化」をクリック。
* 「Hyper-V」と「仮想マシンプラットフォーム」にチェックを入れてOKをクリックしインストールの上、システムを再起動。

3. Ubuntuのインストール前設定

* 「スタート」ボタン横の検索ボックスに"Hyperl"と入力。「Hyper-V Manager」を「開く」。
* Hyper-V ManagerでPCのホスト名を選択し、メニュー→「操作」→「新規 」→>「仮想マシン」をクリックし、ウィザードで下記を入力。下記以外は変更せず完了まで実施。
	* 名前   Ubuntu2004
	* 「仮想マシンを別の場所に格納する 」チェック
	* 場所 c:¥workspace¥ubuntu2004 ※フォルダを作成
	* 仮想マシンの世代  「第二世代」を選択
	* 「このマシンで動的メモリを使用します」をオフ
	* 接続  Default Switchを選択
	* 「ブートイメージファイルからオペレーティング・システムをインストールする」を選択し、1. でダウンロードしたisoファイルを選択。
* 作成された仮想マシンを右クリックし「設定」をクリック。下記を設定し「OK」をクリック。
	* 「セキュリティ」を選択し、「セキュアブートを有効にする」チェックボックスをはずす。
	* 「統合サービス」を選択、「ゲストサービス」のチェックをオンに変更する。

4. Ubuntuのインストール

* 仮想マシンを右クリックして「起動」をクリック。	
* 設定のウィザードが始まるため、下記選択しながら先に進む。キーボードの上下とタブキーで移動しリターンキーで選択する。
	* 言語　　　　　　　　　　　「English」を選択
	* Instoller Update Available　  「Continue without updating」選択
	* キーボード　　　　　　　　Japanese
		* TABで移動しリターンで選択肢を表示。上下カーソルでJapaneseを選択し「Done」を選択。　※画面上に見えない場合スクロール
	* ネットワーク
		* 1つ目はインターネット向けのためDHCPのままで良い。
		* 2つ目のネットワークのTABで移動。選択して「Edit IPv4」を選択。「IPv4 ethod」を「Manual」に変更し、下記設定し「Save」を選択し「Done」を選択。環境に合わせて未使用のIPアドレスを選択すること。
			* Subnet  192.168.100.0/24
			* Address 192.168.100.10
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
* シャットダウンをして再起動する。`shutdown -r now`

5. SSHによるアクセス

* Hyper-VでUbuntuが再起動したらユーザIDとパスワードを入力しログイン後、`ip addr`でUbuntuのIPアドレスを把握する。
* スタート→「Power Shell」と入力しPower Shell のターミナル起動（Windows Terminalでもよい）
* ターミナルでUbuntu のログインユーザとIPアドレスを指定してSSHでログインする。以後、sshで接続したターミナルで実行する。
```
ssh ubadmin@IPアドレス
（初回はパスワード認証でよいか聞かれるためyesを入力。パスワードが要求されたらログインpasswordを入力）
```
	
#OpenFastPath/Environment/Fixed