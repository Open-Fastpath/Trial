#!/usr/sh

# dockerのイメージを全て削除する
docker rmi -f `docker images -a -q`
