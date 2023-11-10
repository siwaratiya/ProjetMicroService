FROM openjdk:8
EXPOSE 6992
ADD /target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java", "-jar","user-service.jar"]
#-->  docker build -t user-service .
#-->  docker run -p 8761:8761 user-service