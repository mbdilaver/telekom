FROM adoptopenjdk:11-jre-hotspot

EXPOSE 8080 8081

ARG JAR_FILE=*.jar

ADD ./target/*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]