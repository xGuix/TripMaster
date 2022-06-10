FROM openjdk:11
EXPOSE 8181
WORKDIR /TripMaster/User
COPY build/libs/User-1.0.jar tourguide-user.jar
CMD ["java", "-jar", "tourguide-user.jar"]