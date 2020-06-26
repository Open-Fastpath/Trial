#!/bin/sh

# docker-composeの定義ファイルの読み込み先定義の取得
source ./c9-set_compofile.sh

# docker-composeで定義されているコンテナの稼働状況を確認
docker-compose -p $COMPOSE_PROJECT_NAME ps
