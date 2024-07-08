# Use a base image that has Java installed
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /opt

# Copy the application JAR file to the container
COPY target/*.jar /opt/app.jar

ENV port 9001

# Expose the port that the application will run on
EXPOSE 9001

# Define the command to run the application
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
