#!/bin/sh

# docker-composeの定義ファイルの読み込み先定義の取得
source ./c9-set_compofile.sh

# ビルド済みのコンテナ群をdocker-composeの定義に従い起動
docker-compose -p $COMPOSE_PROJECT_NAME up -d
