
FROM openjdk:8
EXPOSE 7459

ADD target/Commande-0.0.1-SNAPSHOT.jar spring-boot-docker.jar

ENTRYPOINT ["java", "-jar", "spring-boot-docker.jar"]