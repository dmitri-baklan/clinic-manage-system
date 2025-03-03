FROM openjdk:21-jdk
LABEL authors="Dmytro"
WORKDIR /app

COPY target/trainingbase-0.0.1-SNAPSHOT.jar /app/trainingbase-0.0.1-SNAPSHOT.jar

EXPOSE 8088

ENTRYPOINT ["top", "-b"]