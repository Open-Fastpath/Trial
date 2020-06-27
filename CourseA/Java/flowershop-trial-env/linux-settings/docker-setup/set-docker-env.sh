#!/bin/bash

# Dockerインストールに必要なツールをインストール
sudo apt install -y apt-transport-https gnupg-agent
# Dockerを取得に必要なGPGキーを追加する
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
# パッケージの取得元としてdockerの安定版があるサイトを追加する
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
# パッケージリストを最新化する
sudo apt update

