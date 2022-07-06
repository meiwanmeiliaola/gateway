#!/usr/bin/env bash
git pull
mvn package -Dmaven.test.skip=true

PID=$(ps -ef | grep admin-1.0.1.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo Application is already stopped
else
    echo kill $PID
    kill -9 $PID
fi

nohup java -jar -Dspring.profiles.active=prod target/admin-1.0.1.jar &
