FROM openjdk:11
EXPOSE 8004
COPY target/notification-service.jar notification-service.jar
ENTRYPOINT ["java","-jar","/notification-service.jar"]