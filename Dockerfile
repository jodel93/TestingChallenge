FROM openjdk:8u191-jre-alpine3.8
RUN apk add curl jq
#WORKSPACE
WORKDIR /usr/share/testing
#Adding .jar from host
ADD target/angular-strangelist-tests.jar                angular-strangelist-tests.jar
ADD target/libs                                         libs
#Adding suite files
ADD TestFramworkBasicSuite.xml                          TestFramworkBasicSuite.xml
ADD healthcheck.sh                                      healthcheck.sh
#Adding configuration files
ADD src/test/resources                                  src/test/resources

ENTRYPOINT sh healthcheck.sh