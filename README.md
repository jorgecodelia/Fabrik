# Fabrik Rest API

## Overview

This documentation outlines the endpoints available in the Fabrik REST API built with Spring Boot 3 and Java 17.

## API Endpoints
The following endpoints are available:

> - GET /fabrik/api/v1/accounts: Retrieves all accounts.
> - GET /fabrik/api/v1/{accountId}/balance: Retrieves a balance resource by its account ID.
> - GET /fabrik/api/v1/{accountId}/transactions?fromAccountingDate=2020-01-01&toAccountingDate=2020-12-01: Retrieves a Transaccion by an account ID and a specific date.
> - POST /fabrik/api/v1/{accountId}/transfer: Creates a new money transfer.

## Dependencies

| Resource    | Documentation                           | Report                                         |
|:------------|:----------------------------------------|:-----------------------------------------------|
| Java        | https://openjdk.org/projects/jdk/17/    | No                                             |
| Gradle      | https://gradle.org/                     | No                                             |
| Spring Boot | https://spring.io/projects/spring-boot/ | No                                             | 
| Feign       | https://github.com/OpenFeign/feign/     | No                                             | 
| JUnit       | https://junit.org/junit5/               | [Report](/build/reports/tests/test/index.html) | 
| Pitest      | https://pitest.org/                     | [Report](/build/reports/pitest/index.html)     | 
| ArchUnit    | https://www.archunit.org/               | Yes                                            | 
| SpotsBugs   | https://github.com/spotbugs/spotbugs/   | Yes                                            | 
| Checkstyle  | https://checkstyle.sourceforge.io/      | [Report](/build/reports/checkstyle/main.html)  | 
| PMD         | https://pmd.github.io/                  | [Report](/build/reports/pmd/main.html)         | 
| Open API    | https://springdoc.org/                  | Yes                                            | 


## Project Structure

```sh
fabrik-api/
│
├── config                                              // api main config src
├── gradle/                                             // Gradle configurations file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── yourcompany/
│   │   │           └── projectname/
│   │   │               ├── client/                     // Api feign client
│   │   │               ├── common/
│   │   │               │   ├── config/
│   │   │               │   ├── domain/                 // Api general POJO
│   │   │               │   ├── exception/              // Global custom exceptions
│   │   │               │   └── Util/                   // Util
│   │   │               ├── controller/                 // REST API controllers
│   │   │               ├── service/                    // Service layer
│   │   │               └── Application.java            // Main class to run the application
│   │   │
│   │   └── resources/                                  // Project Resources
│   │
│   └── test/                                           // Project Test sources
│
├── .gitignore                                          // git ignore configuration file
├── build.gradle                                        // Gradle build configuration file
└── README.md                                           // Project MD documentation

```

## Base URL

The base URL for all endpoints is `http://localhost:8080/api/fabrik/v1`.

## Endpoint Documentation (WIP)

The openApi URL documentation in local environment is `http://localhost:8080/api/fabrik/v1`.

## BUILD
TODO: implement ArchUnit
```sh
./gradlew clean build test pitest
```

## RUN

```sh
./gradlew bootRun -Dspring.profiles.active=local
```
