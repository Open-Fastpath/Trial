# OpenFastPath環境構築Mac版
ー　 version 2020.06.23　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従い、ご利用のマシンがMacを利用する場合に本手順を用いて環境を構築する。
* 本手順の後に「OpenFastPath環境構築Docker共通設定」と「OpenFastPath環境構築VSCode共通設定」を実施する。 

## トレーニング環境の準備

### 環境準備の流れと所要時間

1. Dockerによる仮想サーバ環境の準備＜10分＞
2. Open JDKのインストール＜10分＞
3. Gradleのインストール＜3分＞
4. Graphvizのインストール＜3分＞
5. Visual Studio Codeのインストール＜5分＞
6. トレーニングコンテンツダウンロード＜5分＞

### Dockerによる仮想サーバ環境の準備
[想定所要時間 10分]

* Docker HubのサイトにてDocker Hubのユーザーを作成し、Docker for Macをダウンロード。何も設定を変更しないでインストール。※インストーラの起動に時間がかかるが待つこと。
[Docker Hub](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)
* Docker for Macを起動。※通知領域にDockerのアイコンが表示されるまで時間がかかるが待つこと。
* Home brewがインストールされていない場合は下記にてHome brewをインストール（管理者パスワードが聞かれるため入力する。）
※「bluestacks」がインストールされている場合アンインストールする
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
```

* dockerをインストール。※docker-composeも導入される。
```
brew install docker
```

* ターミナルを起動し`docker version`にてClientとServer両方が表示されることを確認。

### Open JDKのインストール
[想定所要時間 5分]

Visual Studio CodeからUbuntu上で開発しJavaの入っているコンテナへ今後のトレーニングでリリースをするため、ホストOS上にもOpenJDKをインストールする。

* 「Lanchpad」→「その他」→「ターミナルを選択し起動
* brewにてAdoptOpenJDK11をインストール
```
brew tap AdoptOpenJDK/openjdk
brew cask install adoptopenjdk11
```

* OSがCatlinaの場合（アップルマークからこのMac についてで確認）ターミナルを起動し下記コマンドを実行し、設定ファイル.zprofileの最後に書き加えられたことを確認。
```
echo "export JAVA_HOME=\"/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home\"" >> .zprofile
source .zprofile
cat .zprofile
```

* OSがCatlina以前の場合はターミナルを起動し下記コマンドを実行し、設定ファイル.bashrcの最後に書き加えられたことを確認。
```
echo "export JAVA_HOME=\"/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home\"" >> .bashrc
source .bashrc
cat .bashrc
```

※MacはCatlinaよりシェルがZSHベースになっているため設定が異なる。

* インストール後、JAVA＿HOMEの環境変数で指定されているパスにOpenJDK11がインストールされたことを確認。
```
echo $JAVA_HOME
$JAVA_HOME/bin/java -version
```

```
ーJAVAインストールパスの確認ー
Javaのインストールパスは本トレーニングでは下記に設定している。
echo $JAVA_HOMEの結果が下記ではない場合問い合わせのこと。
Windows + WSL・Ubuntu		"/usr/lib/jvm/java-11-openjdk-amd64"
Mac	"/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home"
```

###  Gradleのインストール

* Gradleをインストールする。バージョンが6以上であることを確認。（MacのHomeBrewでは最新版の6.xがダウンロードされる。）
```
brew install gradle
gradle -v
```

## Graphvizのインストール

* VS CodeでMarkdown上でPlantUMLを有効にするためにGraphvizのライブラリをインストールする。
```
brew install graphviz
```


### Visual Studio Codeのインストール

* Visual Studio Code（以後VS Code）のダウンロードサイトから利用OSに合わせたインストーラをダウンロードし実行。
[Download Visual Studio Code - Mac, Linux, Windows](https://code.visualstudio.com/download)
* インストール完了画面でVisual Studio Codeを起動するを選択しVS Codeを起動。
```
ーVS Codeのクリーンインストールー
VS Codeはアンインストールしただけでは拡張機能や設定のフォルダが削除されないため、下記のフォルダも削除する。

rm -rf '/Applications/Visual Studio Code.app/'
rm -rf '~/Library/Application Support/Code'
rm -rf ~/.vscode/
```

### トレーニングコンテンツの配置

1. トレーニングコンテンツのダウンロード

* Open Fast PathのGitからトレーニングコンテンツをダウンロード
```
cd
git clone https://github.com/Open-Fastpath/Trial.git
```

2. トレーニング環境の配置

* MacのターミナルでHomeディレクトリに移動しワークスペースとなるディレクトリ作成。
```
cd
mkdir workspace
```

* 次世代開発Java版トレーニングの基本設定ファイルをGitよりダウンロードしたファイルから配置。
```
cd ~/workspace
cp -rp ~/Trial/CourseA/Java/flowershop .
```

* Mac用のパスを指定しているファイルを上書きするコピー。（java.homeのパス等）
```
cd ~/workspace/flowershop/forMac
bash copy_forMac.sh
```

#OpenFastPath/Environment/Fixed
