FROM openjdk:11
EXPOSE 8484
WORKDIR /TripMaster/TripPricer
COPY build/libs/TripPricer.jar tripPricerApp.jar
ENV SPRING_PROFILES_ACTIVE=docker
CMD ["java", "-jar", "/tripPricerApp.jar"]