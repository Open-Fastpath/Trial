# OpenFastPath環境構築Docker共通設定
ー　 version 2020.06.22　ー

## 本手順の前提

* 「OpenFastPath次世代開発オリエンテーション」に従いご利用のマシンスペックに合わせた環境構築手順によりGthubからコンテンツのダウンロードとマシンごとの補正が完了していること。

## Dockerサービスのビルドと起動
[想定所要時間 10分]

1. Docker実行環境追加設定

* コンテナのlog保管ディレクトリを作成する。
* DBサーバコンテナ用にMySQLのログファイルを作成し権限を付与。
```
cd ~/workspace
mkdir flowershop/log
mkdir flowershop/log/mysql
mkdir flowershop/log/nginx
cd flowershop/log/mysql
touch mysqld.log
chmod 666 mysqld.log
```

```
--Tips Raspberry PI AMD64の場合のDockerイメージ変更--
Raspberry PIはCPUアーキテクチャがWindows、Macと異なりAMD系であるため変更する必要がある。MySQLはAMDの64bit版がまだ安定版でないため、MySQLのオープンソース版MariaDBのイメージを利用し、OpenJDKもAMD系のイメージを利用する。（GitよりトレーニングキットをダウンロードしforRaspberryPIをコピーすると反映されているため手順不要）

MySQLは`~/workspace/flowershop/docker/compose/ `の`flowershopdb`の`image:`を`jsurf/rpi-mariadb`に変更

OpenJDKは`~/workspace/flowershop/docker/devenv/springboot/Dockerfile`で１行目を`FROM bellsoft/liberica-openjdk-alpine AS builder`に変更
```

2. Dockerイメージのビルドとコンテナの起動

* docker-composeを利用しGithubからダウンロードしたdocker-compose用のymlファイルのコンテナの定義に基づきDockerのイメージをビルドしコンテナを起動。
```
cd ~/workspace/flowershop/docker/util
bash c1-build_and_start_compose.sh
```
※ "bash c1"のみ入力しTabキーを押すとファイル名が補完される。

```
ー　Tips Dockerの定義の配置場所ー
”c1-build_and_start_compose.sh”では、同じフォルダにある”c9-set_compofile.sh”でdocker-composeが利用するymlファイルの場所を指定している。これによりdocker-composeは”~/workspace/flowershop docker/compose”フォルダの下に配置されている”docker-compose_flowerapps.yml”ファイルを参照して実行している。
```

```
ー　Tips cgroupがマウントできないエラーが発生したらー
”cgroups: cannot find cgroup mount destination”と出ている場合には
cgroupsのマウント先がない可能性がある
sudo ls -ltr /sys/fs/cgroup/systemd
を実行しフォルダが存在する場合、Windowsを再起動してもう一度試す。

存在しない場合はフォルダを作成しマウントする。
sudo mkdir /sys/fs/cgroup/systemd
sudo mount -t cgroup -o none,name=systemd cgroup /sys/fs/cgroup/systemd 
対応ぼUbuntuを終了しの後Windowsを再起動する。
```

3. Dockerのコンテナサービスの動作確認

* サーバの起動状態の確認
	* `bash c2-view_compose_status.sh`実行後、コンテナのステータスがいずれも”Up”となっていること。
```
cd ~/workspace/flowershop/docker/util
bash c2-view_compose_status.sh
```

* コンテナのログ出力を確認
	* 全てのコンテナのログが記録されていれば良い。
```
cd ~/workspace/flowershop/docker/util
bash c3-view_compose_log.sh
```

4. Webコンテンツ・リバースプロキシサーバの起動確認

* 	ブラウザで初期ページ表示確認
	* `http://localhost:9100/`にアクセスし、 「Open Fast Path Portal」が表示される。（Raspberry PI の場合はデスクトップーLubuntuでログインしブラウザFirefoxを起動して確認）
* nginxを導入したdockerコンテナのWebコンテンツサーバにログインしログ出力を確認。
	* flowerapp_web_access.logに 「Open Fast Path Portal」にアクセスした時をログが出力されている。
	* flowerapp_web_error.logには間違ったURLなどをブラウザで指定すると出力される。（例えばhttp://localhost:9100/aaa）
```
cd ~/workspace/flowershop/docker/util
bash u5-login-container.sh
(選択肢で3を入力）
/ # ps
/ # cat /var/log/nginx/flowerapp_web_access.log
/ # cat /var/log/nginx/flowerapp_web_error.log
/ # exit

※tailコマンドを使える方はtail -f ファイル名を利用しても良い。
```

* nginxを導入したdockerコンテナのWebコンテンツサーバのログフォルダはホストOS上のログフォルダに割り当てられるように指定されている。ホストOSのログフォルダでも同じように出力されることを確認。
```
cd ~/workspace/flowershop/log/nginx
cat flowerapp_web_access.log
cat flowerapp_web_error.log
```

5. データベースサーバ起動確認

* MySQLを導入したdockerコンテナのDBサーバにログインしログ出力を確認。(RaspberryPIでMaria DBをインストールしている場合、コマンドは一緒だがプロンプトの表示はmysqlではなくMariaDBと表示される)
```
>bash u5-login-container.sh
(選択肢で2を入力）
# mysql -u user -P 59306 -p
(password)password
mysql> use flower_db
mysql> show databases;
(flower_dbが表示される)
mysql> use flower_db
（flower_dbに切り替わったことが表示される）
mysql> select * from aaa;
（存在しないテーブルへのSQLにてエラーとなる）
mysql> quit
# tail -f /var/log/mysql/mysqld.log
（操作した内容がログに出力されていること）
(Ctrl+c)
# exit
```

* MySQLを導入したdockerコンテナのDBサーバのログフォルダはホストOS上のログフォルダに割り当てられるように指定されている。ホストOSのログフォルダでも同じように出力されることを確認。
```
cd ~/workspace/flowershop/log/mysql
tail -f mysqld.log
```

```
今回のコンテナの定義では、WebサーバとDBサーバにコンテナにログインしなくてもホストOSでlogを参照できるようにしている。
これはdocker-composeがビルド時に参照する定義ファイル（~/workshop/flowershow/docker/compose/docker-compose_flowerapps.yml）
のvlumesで設定しているためである。
```

6. アプリケーションサーバ 起動確認

* OpenJDKを導入したdockerコンテナのアプリケーションサーバにログインしOpenJDKがインストールされていることを確認。
```
cd ~/workspace/flowershop/docker/util
>bash u5-login-container.sh
(選択肢で1を入力）
/ # java -version
/ # exit
```

8. Docker Serviceを外部からアクセスできるようにする
	* エディタをデフォルトのnanoからviへ変更する。（機種により選択肢が異なるがvim.basicの番号を選択する。）
```
sudo update-alternatives --config editor

alternative editor (/usr/bin/editor を提供) には 5 個の選択肢があります。

  選択肢    パス              優先度  状態
------------------------------------------------------------
* 0            /bin/nano            40        自動モード
  1            /bin/ed             -100       手動モード
  2            /bin/nano            40        手動モード
  3            /usr/bin/code-oss    0         手動モード
  4            /usr/bin/vim.basic   30        手動モード
  5            /usr/bin/vim.tiny    15        手動モード

現在の選択 [*] を保持するには <Enter>、さもなければ選択肢の番号のキーを押してください: 4
```

	* リモートからdockerデーモンへのアクセスを許可するために編集を実行。（Dockerのデフォルトのポートは2375だがインターネットに接続しているとアタックされるためデフォルトポートは利用しない。）
```
sudo systemctl edit docker.service
```

	* 開いたvimの編集画面（初期表示は空）で下記を貼り付け（ポート番号はDockerインストール時に指定したポート番号に書き換える。「i」で挿入モードにし貼り付けたあと「:wq」で上書き保存し終了。）
```
[Service]
ExecStart=
ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:59998 --containerd=/run/containerd/containerd.sock
```

	* 編集後にデーモンとサービスを再起動
```
sudo systemctl daemon-reload
sudo systemctl restart docker.service
```

	* Ubuntu上でdockerが実行できるか確認。（IPアドレスはUbuntuのIPアドレス`ip addr`で確認。）Hello from Docker!が表示される。
	`docker -H tcp://192.168.xx.xx:59998 run --rm hello-world ` 

#OpenFastPath/Environment/Fixed