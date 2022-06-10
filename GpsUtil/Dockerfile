FROM openjdk:11
EXPOSE 8282
WORKDIR /TripMaster/GpsUtil
COPY build/libs/GpsUtil-1.0.jar tourguide-gpsutil.jar
CMD ["java", "-jar", "tourguide-gpsutil.jar"]