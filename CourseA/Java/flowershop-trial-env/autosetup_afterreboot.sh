#!/bin/sh

echo "Java インストール確認"
echo "JAVA_HOME:$JAVA_HOME"
java -version

echo "Gradle インストール確認"
gradle -v

echo "Docker サービス起動"
sudo service docker start

echo "Docker サービス状態確認"
docker version

echo "MySQLコンテナ生成・起動"
cd 
mkdir -p workspace/flowershop
cp -rp  ~/Trial/CourseA/Java/flowershop-trial-env/docker workspace/flowershop/.
cd workspace/flowershop/docker/util
bash c1-build_and_start_compose.sh
bash c3-view_compose_log.sh
bash c2-view_compose_status.sh

#echo "cgroupマウント"
#sudo mkdir /sys/fs/cgroup/systemd
#sudo mount -t cgroup -o none,name=systemd cgroup /sys/fs/cgroup/systemd

