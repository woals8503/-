#!/usr/bin/env bash

# start.sh
# 서버 구동을 위한 스크립트

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ubuntu/action
PROJECT_NAME=shimpyo  # 어플리케이션 이름 변경

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/deploy/*.jar $REPOSITORY/"

cp $REPOSITORY/deploy/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -9 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 2
fi

echo "> 현재 8081 포트를 사용 중인 프로세스 확인"
EXISTING_PID=$(lsof -t -i:8081)

if [ -z "$EXISTING_PID" ]; then
    echo "> 8081 포트를 사용 중인 프로세스가 없습니다."
else
    echo "> 8081 포트를 사용 중인 프로세스를 종료합니다. PID: $EXISTING_PID"
    sudo kill -9 $EXISTING_PID
    sleep 2
fi

cd /home/ubuntu/action
echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."

nohup java -jar \
        -Dspring.config.location=classpath:/application.yml,/home/ubuntu/action/application-db.yml \
        /home/ubuntu/action/shimpyo-0.0.1-SNAPSHOT.jar > $REPOSITORY/nohup.out 2>&1 &
