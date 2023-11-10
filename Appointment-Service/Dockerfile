FROM openjdk:8

EXPOSE 6791

ADD target/appointment-service-docker.jar appointment-service-docker.jar

ENTRYPOINT ["java", "-jar", "appointment-service-docker.jar"]