FROM openjdk:8
EXPOSE 8088
ADD /target/Api-Gateway-0.0.1-SNAPSHOT.jar Api-Gateway-docker.jar
ENTRYPOINT ["java","-jar","Api-Gateway-docker.jar"]