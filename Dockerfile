# For Java 11, try this
FROM adoptopenjdk/openjdk8:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=target/pms-*.jar

# cd /opt/app
WORKDIR /opt/apps

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]