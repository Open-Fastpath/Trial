#!/bin/sh

# 全てのコンテナを削除する
docker rm `docker ps -a -q`
