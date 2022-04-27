FROM openjdk:11-slim
RUN adduser --system --group dgen
USER dgen:dgen
WORKDIR /app

COPY /target/*.jar dgen.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","dgen.jar"]