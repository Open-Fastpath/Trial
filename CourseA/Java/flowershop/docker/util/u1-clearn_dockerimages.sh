#!/bin/sh

# docker imageを使われていないものはクリーンナップする
docker system prune -f

#docker system prune -a
#docker container prune -f
#docker volume prune -f
#docker network prune -f
#docker image prune -f
