# RELEASE NOTE

## 2020.5.23

### トレーニング環境構築手順修正

* 誤記、余分な手順削除。
* 正式名の記載、メニュー等は括弧で表記。
* Insider Preview ビルドを受け取る前にMicrosoftアカウントを作成しWindowsにログインするように明記。
* プレビュービルドを受け取る頻度はスローでも良いことを明記。
* 再度git cloneする場合はフォルダを削除することを追記。
* docker-composeビルド時にcgroupへのマウントができない場合の対処方法を追記。
* MySQLのlogファイルが書き込まれる様空ファイル作成手順追加。
* MySQLの操作がよりわかりやすくLogの出力確認ができるように手順追加。
* Ubuntu自体にOpen JDKをインストールする意味を追記。
* VS Codeのセットアップ中の実行ディレクトリ明記。
* VS Codeの拡張機能インストール時に最新版、複数種の存在する場合は詳細まで記述。Checkstyleの表記修正。

### お気に入り用アイコン追加

* Webサーバのコンテナのログ確認時にfavicon.icoが存在しないエラーが発生しないようにサイトのお気に入り用アイコンを追加。
* SpringBootのflowershop用index.html用にfavicon.icoを設置

## 2020.5.28

### トレーニング手順修正 - Windowsによるトライアル実施の結果「レビュー_version-20200523.txt」でのご指摘反映

* ビルド18917以降であれば次の「2.WSLの有効化」に進むを修正
* P11、P12のフォルダパスの誤記を修正

## 2020.6.6

### トレーニング手順修正 - Macによるトライアル実施の結果「ofp(mac)メモ,txt」でのご指摘反映

* Home brewのインストール前提を明記
* ディレクトリ移動の手順の明確化
* OpenJDKにおける鉤括弧漏れの訂正
* Mac版は個別にJAVA＿HOMEの環境設定が必要であることを追加
* マシン負荷を考慮しSQLToolsの導入先をMySQLのコンテナではなくUbuntuもしくはMacのホストOS上のVS Codeに導入するよう訂正
* SQLToolsの設定で導入前提となるSQLTools MySQL/MariaDBの導入を追加
* SQLTools確認時にdockerコンテナの起動状況を確認する手順を追加

## 2020.6.12

### トレーニング手順 #1 WSL2版 修正

* WSL2が2020年5月28日正式リリースのWindows Updateにより一般公開されたことによりWondows Insiderプログラムでの導入手順を廃止。（Windows8からのアップグレードPCやBIOS仮想化が元々されていないPCでWindows Update苦戦のため通常状態で可能とする。）

### Windows Home対応 2バージョン新規リリース

Windows Updateが5月28日飯にできないPCおよび、前提となる64bitPCでない、もしくはBIOSで元々仮想化が有効になっていないPC用の手順を追加

1. OpenFastPathトレーニング環境構築手順VirtualBox版.pdf/md
   * 64bitかつBIOS仮想化有効だが、何らかの原因により5月28日飯以降のWindows Updateが適用できずWSL2環境が利用できないPC向けの手順
2. OpenFastPathトレーニング環境構築手順WindowsHome版.pdf/md
   * 64bitPCでない、もしくはBIOSで元々仮想化が有効になっていないPCにコンテナやWindows上のLinuxのインストールは一切ぜずWindowsに直接インストールする手順。

## 2020.6.14

### トレーニング手順 #2 VirtualBox版修正

* 全体の手順の流れが後続と不一致であることの修正
* 全体の手順の流れの直後に不要なメモが混入していた点を修正
* Ubuntuの初期設定ーパッケージの初期化にて「スタートの横の検索窓でUbuntuと入力し・・・起動」とWLS版の手順が残っていた点を削除
* Visual Studio Codeによる開発環境の準備にて、データベースへの接続時にSQLToolsを導入済みだが、後ろの「拡張機能」を下記からインストールで再度SQLToolsが対象になっていた点を削除

## 2020.6.22

### トレーニング手順 Windows Home 直インストール版修正

* タイトルを「Open Fast Pathトレーニング環境構築手順Win10Home32bit-BIOS仮想化不可版」から「OpenFastPath環境構築WindowsHome直インストール版」に変更
* MySQLのインストールを5.7系と8系の両方に対応
* GitクライアントのインストールにてVS Codeをデフォルトとする手順削除
* Gitクライアントのインストールにおける誤字修正
* Gitクライアントのインストーラの問題が発生していない場合の記載を追記
* VS CodeのJava環境設定における手順誤りと不要な手順修正
* VS CodeのSQL Toolsのインストールと設定にて設定名称修正と不要な手順削除
* VS Codeその他拡張機能のインストールと設定タイトル修正
* 全体の流れから別紙に移動したトライアルトレーニングのダウンロード削除

