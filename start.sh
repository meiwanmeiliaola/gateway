#!/usr/bin/env bash
nohup java -jar -Dspring.profiles.active=prod target/admin-1.0.1.jar &
