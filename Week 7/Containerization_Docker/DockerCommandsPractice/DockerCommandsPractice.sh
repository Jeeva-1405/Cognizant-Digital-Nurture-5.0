#!/bin/bash

docker build -t jeeva1405/employee-management-service:1.0 .

docker images

docker run -d --name employee-service -p 8080:8080 jeeva1405/employee-management-service:1.0

docker ps

docker logs employee-service

docker exec -it employee-service /bin/sh

docker stop employee-service
docker start employee-service

docker run -d --name test-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=employeedb \
  -p 3306:3306 \
  mysql:8.0

docker volume create employee-data
docker run -d --name employee-service-persist \
  -v employee-data:/app/data \
  -p 8081:8080 \
  jeeva1405/employee-management-service:1.0

docker volume ls
docker volume inspect employee-data

docker network create employee-net
docker network ls
docker network inspect employee-net

docker run -d --name employee-service-networked \
  --network employee-net \
  -p 8082:8080 \
  jeeva1405/employee-management-service:1.0

docker stop employee-service employee-service-persist employee-service-networked test-mysql
docker rm employee-service employee-service-persist employee-service-networked test-mysql

docker rmi jeeva1405/employee-management-service:1.0

docker system prune -f
