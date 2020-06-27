# Training Environment

## トレーニング環境用のWSL・Ubuntu用の設定を提供する

1. Ubuntuのユーザ設定ファイルの拡張
  
    .barhrc

    * Ubuntu版JavaインストールディレクトリーJAVA_HOME
    * ubuntuのdockerクライアントからDocker for WindowsのdockerサーバへのアクセスURIとポート設定
    * Ubuntuのターミナル表示色設定ファイルへの参照

2. Ubuntuのターミナル表示設定ファイル
    
    .colorrc

3. Vimエディタの拡張設定

    ,vimrc
    .vim (設定格納ディレクトリ）

    * Vim全体の表示色をsolarizedのすたいるに変更
    * ファイルタイプ別色強調

4. Ubuntu docker最新版インストール用自動設定

    * AMD64用Dockerインストールに必要なツール(apt-transport-https gnupg-agent) のインストール
    * Dockerを取得に必要なGPGキーの追加
    * パッケージの取得元としてdockerの安定版があるサイトの追加
    * パッケージリストの最新化


