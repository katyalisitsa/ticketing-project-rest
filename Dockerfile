FROM openjdk:11-jdk
COPY ./target/SpringMVC-ProjectManagement-0.0.1-SNAPSHOT.jar  /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch SpringMVC-ProjectManagement-0.0.1-SNAPSHOT.jar'
EXPOSE 8080
ENTRYPOINT ["java","-jar","SpringMVC-ProjectManagement-0.0.1-SNAPSHOT.jar"]