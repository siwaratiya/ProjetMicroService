FROM openjdk:8


EXPOSE 6579

ADD target/spring-boot-docker.jar spring-boot-docker.jar

ENTRYPOINT ["java", "-jar", "spring-boot-docker.jar"]