# ボリュームリソースを削除する
# docker down -v

docker volume rm $(docker volume ls -qf dangling=true)
docker volume prune
