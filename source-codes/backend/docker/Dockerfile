# Dockerfile microservice

# install the base image
FROM openjdk:8-jdk-alpine

# create directory in the container
RUN mkdir /app 

# copy called.jar into the newly created directory
ADD microservice.jar /app/

# establish this directory as the working directory
WORKDIR /app 

# launch the microservice
CMD ["java", "-jar", "/app/microservice.jar"]
