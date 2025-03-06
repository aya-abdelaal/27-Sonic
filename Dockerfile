# Use OpenJDK 25 Early Access with Oracle Linux 9 as the base image
FROM openjdk:25-ea-4-jdk-oraclelinux9

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project to the container (ensure unnecessary files are ignored using .dockerignore)
COPY ./ /app

# Set environment variable for JSON file path
ENV DATA_JSON_PATH="/app/data/data.json"

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "/app/target/mini1.jar"]
