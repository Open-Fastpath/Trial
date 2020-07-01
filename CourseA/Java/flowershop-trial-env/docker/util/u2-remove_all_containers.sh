#!/bin/sh

# 全てのコンテナを削除する
docker rm -f `docker ps -a -q`
