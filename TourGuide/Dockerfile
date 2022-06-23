FROM openjdk:11-slim
EXPOSE 8080
RUN times
COPY build/libs/tourguide-1.0.jar app.jar
CMD [ "java", "-jar", "/app.jar" ]