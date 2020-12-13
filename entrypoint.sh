#!/bin/sh
# mvn clean install
# mvn clean package

# mvn install
# mvn clean
# mvn -e package 

echo "start jar"
# java -jar ./target/apse-0.0.1-SNAPSHOT.jar
echo "start source"
mvn spring-boot:run
