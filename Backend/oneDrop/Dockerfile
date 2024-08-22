FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/OneDrop.jar
ENV DB_CLEVER_ONEDROP_USERNAME=ucscdbngtzxdr0p3
ENV DB_CLEVER_ONEDROP_PASSWORD=V5dLp8D54m6iJmvAG3xX
COPY ${JAR_FILE} OneDropServer.jar
ENTRYPOINT ["java", "-jar", "OneDropServer.jar"]
EXPOSE 8080
