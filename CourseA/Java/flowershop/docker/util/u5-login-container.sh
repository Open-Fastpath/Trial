#!/bin/sh

PS3="ログインするコンテナを選択ください(1-5)"
select CONTAINER_NAME in flower_app_springboot flower_db_mysql flower_web_nginx flower_app_tomcat quit
do
    echo " select no $REPLY value $CONTAINER_NAME"
    if [ "$REPLY" = "5" ]; then
        exit 0;
    elif [ "$REPLY" = "1" ] || \
         [ "$REPLY" = "2" ] || \
         [ "$REPLY" = "3" ] || \
         [ "$REPLY" = "4" ]; then
        break;
    fi

done

# 指定されたコンテナにログインする
docker exec -it $CONTAINER_NAME /bin/sh
