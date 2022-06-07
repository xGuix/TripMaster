FROM openjdk:11
EXPOSE 8383
WORKDIR /TripMaster/RewardCentral
COPY build/libs/RewardCentral.jar rewardCentralApp.jar
ENV SPRING_PROFILES_ACTIVE=docker
CMD ["java", "-jar", "/rewardCentralApp.jar"]