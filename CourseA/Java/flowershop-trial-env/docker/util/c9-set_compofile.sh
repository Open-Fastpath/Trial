#!/bin/sh

# docker-composeで起動するプロジェクト名を指定
export COMPOSE_PROJECT_NAME=flower-trial-pj

# docker-composeが参照するコンテナの定義ファイルを指定
export COMPOSE_FILE=~/workspace/flowershop/docker/compose/docker-compose_flowerapps-trial.yml
