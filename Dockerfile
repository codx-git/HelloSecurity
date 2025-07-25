FROM openjdk:17-slim

COPY security/target/security-0.0.1-SNAPSHOT.jar /app.jar

ENV PARAMS=""

ENTRYPOINT ["sh","-c","java -jar /app.jar $PARAMS"]