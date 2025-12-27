# ================================
# 1. Build Stage
# ================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -q -e -DskipTests package

# ================================
# 2. Runtime Stage
# ================================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/inventory-service-1.0.0.jar app.jar

EXPOSE 8082

#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
