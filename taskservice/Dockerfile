# Use a Java base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/taskservice-0.0.1-SNAPSHOT.jar /app/taskservice.jar

# Expose the port the app will run on
EXPOSE 4000

# Run the JAR file
ENTRYPOINT ["java", "-jar", "taskservice.jar"]
