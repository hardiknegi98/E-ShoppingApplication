FROM openjdk:11
EXPOSE 8100
COPY target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]