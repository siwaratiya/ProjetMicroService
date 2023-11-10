FROM openjdk:8
EXPOSE 8761
ADD /target/Eureka-discovery-0.0.1-SNAPSHOT.jar Eureka-discovery-docker.jar
ENTRYPOINT ["java","-jar","Eureka-discovery-docker.jar"]