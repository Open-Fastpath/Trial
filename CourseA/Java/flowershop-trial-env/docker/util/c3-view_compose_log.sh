#!/bin/sh

# docker-composeの定義ファイルの読み込み先定義の取得
source ./c9-set_compofile.sh

# dockser-composeで起動されたコンテナのログ出力
docker-compose -p $COMPOSE_PROJECT_NAME logs
