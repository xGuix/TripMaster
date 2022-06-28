# TourGuide by TripMaster
**ALPHA project:TourGuide-v1.0**

**Tour Guide: "Your city guide"** <br>
TourGuide is a Spring Boot application, will targeting people looking for package deals on hotel stays and admissions to various attractions.
New Update now supports the tracking of 100.000 users under 3 minutes and calculate reward points for 100.000 users under 2 minutes.


## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development
and testing purposes.</br>
See deployment for notes on how to deploy the project on a live system.


### App built with
What things you need to install this App
1. Java 11 OpenJDK
2. Spring Boot v2.7.0
3. Gradle 7.4.2
4. Docker 4.9.1
5. Docker compose 3.3
6. JUnit 5.8.2
7. Jacoco 0.8.8
8. Log4j


### Installing
A step by step series of examples that tell you how to get a development env running:

1.Install Java:
https://www.oracle.com/java/technologies/downloads/#java11

2.Install Gradle:
https://gradle.org/install/

3.Install SpringBoot:
https://spring.io/projects/spring-boot

4.Install Docker:
https://www.docker.com/get-started/

After downloading and installing them, no specific setup required.


### Data Architecture
Micro-services in tour guide project:
* TourGuide
* User
* GpsUtil
* RewardCentral
* TripPricer 

All Deployed in with Docker

### Starting application
Build all jar from main directory :
```
./gradlew bootJar
```

Launch command in terminal:
```
docker-compose up
```

Shutdown server:
```shell
Ctrl + C
```

Stop and remove containers, networks:
```
docker-compose down
```


### URL Access
TourGuide URL allow access to all url containers

Open your browser and get url:
```
https://0.0.0.0:8080/
```

### Application Open Ports
- User: ```https://0.0.0.0:8181```
- GpsUtil: ```https://0.0.0.0:8282```
- RewardCentral: ```https://0.0.0.0:8383```
- TripPricer: ```https://0.0.0.0:8484```

### Testing
The existing tests need to be triggered from maven-surefire plugin while we try to generate the final executable jar file.<br>
Run the tests from gradle, go to the folder "/TripMaster" that contains the gradlew file and execute the below command.

* Run tests, use command:
```shell
gradle test
```


### Reports
Auto generate with tests to get jacoco report:
- **Jacoco Report** for tests coverage.

Access file directory : `TourGuide/build/jacocoHtml` <br>
Run the `index.html` in your web browser.


### Jacoco Coverage
Jacoco coverage is automatically check with tests.
**Successfully over 50% passed**