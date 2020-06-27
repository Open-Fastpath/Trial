#!/bin/sh

# パッケージリストの更新
sudo apt update
# インストール済みパッケージのアップグレード
sudo apt upgrade -y
# 日本語化 Trialはマニュアルなし
sudo apt -y install language-pack-ja
sudo update-locale LANG=ja_JP.UTF8
# JDK インストール
sudo apt install -y openjdk-11-jdk
# Gradle インストール
sudo add-apt-repository ppa:cwchien/gradle -y
sudo apt-get update
sudo apt -y install gradle
# Graphviz インストール
sudo apt -y install graphviz
# Docker インストール
cd ~/Trial/CourseA/Java/flowershop-trial-env
bash linux-settings/docker-setup/set-docker-env.sh
sudo apt install docker-ce docker-ce-cli containerd.io docker-compose -y
sudo usermod -aG docker $USER
sudo gpasswd -a $USER docker
# 表示設定更新
cd ~/Trial/CourseA/Java/flowershop-trial-env
cp -p linux-settings/.bashrc .
cp -rp linux-settings/.vim* .
cp -p linux-settings/.colorrc .
# 再起動
sudo reboot
