FROM openjdk:11
EXPOSE 8282
WORKDIR /TripMaster/GpsUtil
COPY build/libs/GpsUtil.jar GpsUtilApp.jar
ENV SPRING_PROFILES_ACTIVE=docker
CMD ["java", "-jar", "/GpsUtilApp.jar"]