#!/bin/sh

# Springアプリケーションサーバ DBサーバ Webサーバのコンテナを削除
# イメージは削除しない
docker rm -f flower_app_springboot flower_db_mysql flower_web_nginx flower_app_tomcat
# ネットワークを削除
docker network rm flowerpj_flower_web_segment
