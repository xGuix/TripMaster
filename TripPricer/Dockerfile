FROM openjdk:11
EXPOSE 8484
WORKDIR /TripMaster/TripPricer
COPY build/libs/TripPricer-1.0.jar tourguide-trippricer.jar
CMD ["java", "-jar", "tourguide-trippricer.jar"]