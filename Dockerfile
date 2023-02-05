#
# Build stage
#
#FROM maven:3.8.2-jdk-11 AS build
#COPY . .
#RUN mvn clean package -DskipTests
#
##
## Package stage
##
#FROM openjdk:11-jdk-slim
#COPY --from=build /target/fintech-payment-api-sqd11b-0.0.1-SNAPSHOT.jar fintech-payment-api-sqd11b-0.0.1-SNAPSHOT.jar
# ENV PORT=8080

FROM openjdk:11
ADD target/fintech-payment-api-sqd11b-0.0.1-SNAPSHOT.jar fintech-payment-api-sqd11b-0.0.1-SNAPSHOT.jar
#RUN mvn clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fintech-payment-api-sqd11b-0.0.1-SNAPSHOT.jar"]