FROM openjdk:11
EXPOSE 8080
WORKDIR /TripMaster/TourGuide
COPY build/libs/TourGuide-1.0.jar tourguide-app.jar
CMD ["java", "-jar", "tourguide-app.jar"]