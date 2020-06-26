# Training Environment

## 次世代開発トレーニングJava編・トレーニング環境

1. トレーニング環境の準備

    トレーニング環境の準備手順は[こちら](/Training%20Trial%20for%20Open%20Fast%20Path%20%231.pdf)参照。

2. トレーニングキットの構成

ディレクトリ | 概要
-----|---------------------------
code | VS Code設定、Java開発用設定、ソースコード
docker | Docker / Docker Compose設定・操作ユーリティ
htdocs | WebコンテンツHTML、CSS、JavaScript

3. フォルダ構成

```
├── code
│   └── javaapps
│       └── flowershop　VS Code用開発ディレクトリ
│           ├── bin
│           │   ├── main
│           │   │   ├── application.properties
│           │   │   └── jp
│           │   │       └── flowlist
│           │   │           └── flowershop
│           │   └── test
│           │       └── jp
│           │           └── flowlist
│           │               └── flowershop
│           ├── build.gradle
│           ├── gradle
│           │   └── wrapper
│           │       ├── gradle-wrapper.jar
│           │       └── gradle-wrapper.properties
│           ├── gradlew
│           ├── gradlew.bat
│           ├── settings.gradle
│           └── src
│               ├── main
│               │   ├── java
│               │   │   └── jp
│               │   │       └── flowlist
│               │   │           └── flowershop
│               │   └── resources
│               │       └── application.properties
│               └── test
│                   └── java
│                       └── jp
│                           └── flowlist
│                               └── flowershop
├── docker
│   ├── compose　ー　Docker Compose 設定ファイル
│   │   ├── docker-compose_flowerapps.yml
│   │   └── win_docker-compose_flowerapps.yml
│   ├── devenv
│   │   ├── mysql　ー　DBサーバ　MySQL設定ファイル
│   │   │   ├── conf.d
│   │   │   │   └── my.cnf
│   │   │   └── flower_mysql.env
│   │   ├── nginx　ー　Webコンテンツサーバ　Nginx設定ファイル
│   │   │   └── config
│   │   │       └── nginx_flower.conf
│   │   ├── springboot　アプリケーションサーバ SpringBoot Docker/設定ファイル
│   │   │   ├── Dockerfile
│   │   │   └── flower_spring_mysql.env
│   │   └── tomcat　
│   │       └── Dockerfile
│   └── util　ー　Docker / Docker Compose 簡易操作用ユーティリティシェル
│       ├── c1-build_and_start_compose.sh
│       ├── c2-view_compose_status.sh
│       ├── c3-view_compose_log.sh
│       ├── c4-restart_container.sh
│       ├── c5-remove_flowershop_containers.sh
│       ├── c6-start_container.sh
│       ├── c7-stop_container.sh
│       ├── c9-set_compofile.sh
│       ├── u1-clearn_dockerimages.sh
│       ├── u2-remove_all_containers.sh
│       ├── u3-view_docker_network.sh
│       ├── u4-remove_all_dockerimages.sh
│       ├── u5-login-container.sh
│       └── win_hostvolume_copy.sh
├── forMac　ー　Mac用設定ファイル
│   ├── code
│   │   └── javaapps
│   │       └── flowershop
│   ├── copy_forMac.sh
│   └── docker
│       └── util
│           └── c9-set_compofile.sh
└── htdocs　ー　Webコンテンツサーバ コンテンツルートディレクトリ
    ├── css
    │   └── butterCake.min.css
    └── index.html

```

