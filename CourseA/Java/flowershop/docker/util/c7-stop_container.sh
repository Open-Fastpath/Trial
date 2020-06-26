#!/bin/sh

# docker-composeの定義ファイルの読み込み先定義の取得
source ./c9-set_compofile.sh

# docker-composeの定義に従いコンテナ起動しているコンテナを停止
docker-compose -p $COMPOSE_PROJECT_NAME down
