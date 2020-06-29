#!/bin/sh

# パッケージリストの更新
echo "パッケージリストの更新"
sudo apt update
# インストール済みパッケージのアップグレード
echo "インストール済みパッケージのアップグレード"
sudo apt upgrade -y
# 日本語化 Trialはマニュアルなし
echo "日本語化"
sudo apt -y install language-pack-ja
sudo update-locale LANG=ja_JP.UTF8
sudo apt -y install manpages-ja manpages-ja-dev
# JDK インストール
echo "JDK インストール"
sudo apt install -y openjdk-11-jdk
# Gradle インストール
echo "Gradle インストール"
sudo add-apt-repository ppa:cwchien/gradle -y
sudo apt-get update
sudo apt -y install gradle
# Graphviz インストール
echo "Graphviz インストール"
sudo apt -y install graphviz
# Docker インストール
echo "Docker インストール"
sudo apt -y install graphviz
cd ~/Trial/CourseA/Java/flowershop-trial-env
bash linux-settings/docker-setup/set-docker-env.sh
sudo apt install docker-ce docker-ce-cli containerd.io docker-compose -y
sudo usermod -aG docker $USER
sudo gpasswd -a $USER docker
# 表示設定更新
echo "表示設定更新"
cd ~/Trial/CourseA/Java/flowershop-trial-env
cp -p linux-settings/.bashrc ~/.
cp -rp linux-settings/.vim* ~/.
cp -p linux-settings/.colorrc ~/.
# 再起動
echo "再起動"
sudo reboot
