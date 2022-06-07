FROM openjdk:11
EXPOSE 8080
WORKDIR /TripMaster/TourGuide
COPY build/libs/TourGuide.jar tourGuideApp.jar
ENV SPRING_PROFILES_ACTIVE=docker
CMD ["java", "-jar", "/tourGuideApp.jar"]