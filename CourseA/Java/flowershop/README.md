# Training Environment

## 次世代開発トレーニングJava編・トレーニング環境

1. トレーニング環境の準備

    トレーニング環境の準備手順は[こちら](https://github.com/Open-Fastpath/Trial/tree/master/CourseA/Java/flowershop/doc)参照。

2. トレーニングキットの構成

ディレクトリ | 概要
-----|---------------------------
code | VS Code設定、Java開発用設定、ソースコード
doc  | 環境構築手順
docker | Docker / Docker Compose設定・操作ユーリティ
htdocs | WebコンテンツHTML、CSS、JavaScript
forMac | Mac用設定
forRaspberryPI | Raspberry PI用設定

3. フォルダ構成

```
├── code
│   └── javaapps
│       └── flowershop　VS Code用開発ディレクトリ
│           ├── .vscode
│           ├── gradle
│           │   └── wrapper
│           └── src
│               ├── main
│               │   ├── java
│               │   │   └── jp
│               │   │       └── flowlist
│               │   │           └── flowershop
│               │   └── resources
│               └── test
│                   └── java
│                       └── jp
│                           └── flowlist
│                               └── flowershop
├── doc  ー 環境構築手順書
├── docker
│   ├── compose　ー　Docker Compose 設定ファイル
│   ├── devenv
│   │   ├── mysql　ー　DBサーバ　MySQL設定ファイル
│   │   │   └── conf.d
│   │   ├── nginx　ー　Webコンテンツサーバ　Nginx設定ファイル
│   │   │   └── config
│   │   └── springboot　アプリケーションサーバ SpringBoot Docker/設定ファイル
│   └── util　ー　Docker / Docker Compose 簡易操作用ユーティリティシェル
├── forMac　ー　Mac用設定ファイル
├── forRaspberryPI　ー　Raspberry PI用設定ファイル
└── htdocs　ー　Webコンテンツサーバ コンテンツルートディレクトリ
    └── css

```

