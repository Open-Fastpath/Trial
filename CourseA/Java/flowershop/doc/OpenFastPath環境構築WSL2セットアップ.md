# OpenFastPath環境構築WSL2セットアップ
ー　 version 2020.06.26　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンが64bit BIOS仮想化可能で、 Windows Update 2004 May 28以上適用可能（Home / Pro共通）な場合に本手順を用いてWSL2上にUbuntuをセットアップする。
* セットアップ後は「OpenFastPath環境構築Ubuntu共通」にてトレーニング環境を構築する。

## WSL2・UbuntuによるLinux環境の準備
[想定所要時間 15分]

Windowsをトレーニング環境とする場合はWindows上でLinuxを動かすことができるWindows Subsystem for Linux（WSL）を利用しLinuxの種類であるUbuntuを動作できるようにする。

1. WSL2を前提としたWindowsバージョンの確認とWindows Update

* Windowsボタン＋Rでファイル名を指定して実行を開き、`winver`コマンドを実行。Windowsのビルドバージョンがバージョン2004（19624）以上であることを確認する。
* バージョンが2004未満の場合は[Windows 10 のダウンロード](https://www.microsoft.com/ja-jp/software-download/windows10)にて「今すぐアップデート」をクリックし最新に更新する。

2. WSL2の有効化

* 	「スタート」→「-設定」→「アプリ」を開き左メニューより「アプリと機能」をクリック。
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

* 「スタート」ボタン横の検索ボックスに”Ubuntu”と入力。
* 「Ubntu20.04 LTS」を選び「管理者をして実行」をクリックし起動。※Ubuntuは以後必ず管理者権限で起動する。
* 起動中にユーザIDとパスワードが要求されるためUbuntu用の管理ユーザーとパスワードをセットする。（ubadmin/ubadminpw）
* Ubuntuターミナルの左上アイコンをクリックし「プロパティ」をクリック。プロパティで「Ctrl+Shift+C/Vをコピー/貼り付けとして使用する」をチェックする。（以後ターミナル上でコピーはCtrl＋Shift＋C、貼り付けはCtrl+Shift+Vを利用できる。）
* * Power Shellのターミナルが終了している場合は、「スタート」ボタン横の検索ボックスに"power shell"と入力。「Power shell」を選び「管理者として実行」をクリックして起動。
* UbuntuがWSL2でインストールされていることを確認する。”Ubntu-20.04” の”VERSION”が”2”と表示されていればよい。
`wsl -l -v` 

5. Open SSHのインストール

* SSH接続が可能となるようにOpen SSHをインストール
```
sudo apt install openssh-server
```

#OpenFastPath/Environment/Fixed
