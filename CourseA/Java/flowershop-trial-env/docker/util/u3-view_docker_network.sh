#!/bin/sh

# dockerの仮想ネットワークのリストを取得
docker network ls

# flowershopのdocker-composeで指定しているネットワークセグメントの
# 各コンテナの状況を詳細に表示
docker network inspect compose_flower_web_segment
