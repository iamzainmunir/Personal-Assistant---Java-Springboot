# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/UserService-0.0.1-SNAPSHOT.jar /app/user-service.jar

# Expose the port the app will run on
EXPOSE 3000

# Command to run the application
ENTRYPOINT ["java", "-jar", "user-service.jar"]
