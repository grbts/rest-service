FROM amazoncorretto:11-alpine-jdk
MAINTAINER baeldung.com
EXPOSE 8080
COPY target/rest-service-0.0.1-SNAPSHOT.jar rest-service.jar
ENTRYPOINT ["java","-jar","/rest-service.jar"]
