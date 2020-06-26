#!/bin/sh

PS3="再起動するコンテナを選択ください(1-3)"
select CONTAINER_NAME in flower_app_springboot flower_db_mysql flower_web_nginx quit
do
    echo " select no $REPLY value $CONTAINER_NAME"
    if [ "$REPLY" = "4" ]; then 
        exit 0;
    elif [ "$REPLY" = "1" ] || \
         [ "$REPLY" = "2" ] || \
         [ "$REPLY" = "3" ]; then
        break;
    fi

done

# 引数に指定されたコンテナを再起動
docker restart $CONTAINER_NAME  
