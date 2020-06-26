# OpenFastPath環境構築手順VMwareセットアップ
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンが64bit BIOS仮想化可能で、 Windows Update 2004 May 28以上が適用不可であり、Windows Home などでHyper-Vが利用不可な場合に本手順を用いてVMware Workstation Player上にUbuntuをセットアップする。
* * セットアップ後は「OpenFastPath環境構築Ubuntu共通」にてトレーニング環境を構築する。

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. VMware・UbuntuによるLinux環境の準備 ＜20分＞

### VMware・UbuntuによるLinux環境の準備
[想定所要時間 20分]

Windows Homeをトレーニング環境とする場合はWindows上でLinuxを動かすことができるように、VMwareをインストールし、仮想化環境の上でLinuxの種類であるUbuntuを動作できるようにする。

1. Ubuntuののダウンロード
* [Download Ubuntu Server | Download | Ubuntu](https://ubuntu.com/download/server)よりUbuntu Server20.04 LTSのisoファイルをダウンロード

2. ~VMwareのダウンロードとインストール~
* *[VMware Workstation Player のダウンロード | VMware | JP](https://www.vmware.com/jp/products/workstation-player/workstation-player-evaluation.html)よりVMware Workstation Playerをダウンロードし、 インストールフォルダの`C:¥workspace¥vmware`への変更と拡張キーボードのインストールにチェックする以外は全てデフォルトのままインストール。
* VMware Workstation Playerのインストール完了画面で非商用利用であるため「ライセンス」をクリックし「スキップ」を選択、「完了」し、システムを再起動
* VMware Workstation Playerを起動し、「非営利目的で・・・無償で使用する」のまま「続行」を選択、「完了」する。

3. ~Ubuntuのインストール前設定~
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

* VMWareにSSHアクセスできるようにするため、VMwareの仮想マシンのリストからインストールしたUbuntuを右クリックし「設定」を開き、「ネットワークアダプタ」を選択。「ブリッジ:物理ネットワークに直接接続」、「物理ネットワーク接続の状態を複製」を選ぶ。
* VMwareのリストから、Ubuntuを選び、再生ボタンをクリック。
* Ubuntuが起動したらユーザIDとパスワードを入力しログイン後、`ip addr`でUbuntuのIPアドレスを把握する。
* スタート→「Power Shell」と入力しPower Shell のターミナル起動（Windows Terminalでもよい）
* ターミナルでUbuntu のログインユーザとIPアドレスを指定してSSHでログインする。以後、sshで接続したターミナルで実行する。
```
ssh ubadmin@IPアドレス
（初回はパスワード認証でよいか聞かれるためyesを入力。パスワードが要求されたらログインpasswordを入力）
```
	
#OpenFastPath/Environment/Fixed