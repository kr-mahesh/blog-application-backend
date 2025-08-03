# Use a Java 17 base image
FROM eclipse-temurin:24-jdk

# Set working directory to the Spring Boot app folder
WORKDIR /app/blogapp

# Copy the entire project into the container
COPY . .

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Build the project, skipping tests
RUN ./mvnw clean package -DskipTests

# Run the JAR file
CMD ["java", "-jar", "target/blogapp-0.0.1-SNAPSHOT.jar"]