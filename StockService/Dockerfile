FROM openjdk:8
EXPOSE 8180
ADD /target/StockService-0.0.1-SNAPSHOT.jar StockService-docker.jar

ENTRYPOINT ["java", "-jar","StockService-docker.jar"]