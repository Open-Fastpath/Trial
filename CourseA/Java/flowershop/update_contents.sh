#!/bin/sh

rm -fr ~/workspace/flowershop/docker
rm -fr ~/workspace/flowershop/htdocs
rm -fr ~/workspace/flowershop/code/javaapps/flowershop/src

cp -rp ~/workspace/flowershop-nextdev001/docker ~/workspace/flowershop/.
cp -rp ~/workspace/flowershop-nextdev001/htdocs ~/workspace/flowershop/.
cp -rp ~/workspace/flowershop-nextdev001/code/javaapps/flowershop/src ~/workspace/flowershop/code/javaapps/flowershop/.

echo "contents updated."
