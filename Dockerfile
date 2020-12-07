FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=*.jar

COPY ${JAR_FILE} n.jar

ENTRYPOINT ["java", "-jar", "application.jar"]