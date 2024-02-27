# Fabrik Rest API

## Overview

This documentation outlines the endpoints available in the Fabrik REST API built with Spring Boot 3 and Java 17.

## Dependencies

The following report links work once the api is compiled in local

| Resource    | Documentation                           | Report                                                           |
|:------------|:----------------------------------------|:-----------------------------------------------------------------|
| Java        | https://openjdk.org/projects/jdk/17/    | No                                                               |
| Gradle      | https://gradle.org/                     | No                                                               |
| Spring Boot | https://spring.io/projects/spring-boot/ | No                                                               | 
| Feign       | https://github.com/OpenFeign/feign/     | No                                                               | 
| JUnit       | https://junit.org/junit5/               | [Report](/build/reports/tests/test/index.html)                   | 
| Pitest      | https://pitest.org/                     | [Report](/build/reports/pitest/index.html)                       | 
| ArchUnit    | https://www.archunit.org/               | Yes                                                              | 
| SpotsBugs   | https://github.com/spotbugs/spotbugs/   | Yes                                                              | 
| Checkstyle  | https://checkstyle.sourceforge.io/      | [Report](/build/reports/checkstyle/main.html)                    | 
| PMD         | https://pmd.github.io/                  | [Report](/build/reports/pmd/main.html)                           | 
| Open API    | https://springdoc.org/                  | [Report](http://localhost:8080/fabrik/api/swagger-ui/index.html) | 


## Project Structure

```sh
fabrik-api/
│
├── config                                              // api main config src
├── gradle/                                             // Gradle configurations file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.fabrik.api/
│   │   │      ├── client/                              // Api feign client
│   │   │      ├── common/
│   │   │      │   ├── config/                          // General configurations
│   │   │      │   ├── domain/                          // Api general POJO
│   │   │      │   ├── exception/                       // Global custom exceptions
│   │   │      │   └── Util/                            // Util
│   │   │      ├── controller/                          // REST API controllers
│   │   │      ├── service/                             // Service layer
│   │   │      └── Application.java                     // Main class to run the application
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

## API Endpoints
The following endpoints are available:

* GET /fabrik/api/v1/accounts: Retrieves all accounts.
```sh
curl --location 'http://localhost:8080/fabrik/api/v1/accounts' \
--header 'Content-Type: application/json'
```
* GET /fabrik/api/v1/{accountId}/balance: Retrieves a balance resource by its account ID.
```sh
curl --location 'http://localhost:8080/fabrik/api/v1/14537780/balance' \
--header 'Content-Type: application/json'
```
* GET /fabrik/api/v1/{accountId}/transactions?fromAccountingDate=2020-01-01&toAccountingDate=2020-12-01: Retrieves a Transaccion by an account ID and a specific date.
```sh
curl --location 'http://localhost:8080/fabrik/api/v1/14537780/transactions?fromAccountingDate=2020-01-01&toAccountingDate=2020-12-01' \
--header 'Content-Type: application/json'
```
* POST /fabrik/api/v1/{accountId}/transfer: Creates a new money transfer.
```sh
curl --location 'http://localhost:8080/fabrik/api/v1/14537780/transfer' \
--header 'Content-Type: application/json' \
--data '{
    "creditor": {
        "name": "John Doe",
        "account": {
            "accountCode": "IT23A0336844430152923804660",
            "bicCode": "SELBIT2BXXX"
        },
        "address": {
            "address": null,
            "city": "Rome",
            "countryCode": "39"
        }
    },
    "executionDate": "2019-04-01",
    "uri": "REMITTANCE_INFORMATION",
    "description": "Payment invoice 75/2017",
    "amount": 800,
    "currency": "EUR",
    "isUrgent": false,
    "isInstant": false,
    "feeType": "SHA",
    "feeAccountId": "45685475",
    "taxRelief": {
        "taxReliefId": "L449",
        "isCondoUpgrade": false,
        "creditorFiscalCode": "56258745832",
        "beneficiaryType": "NATURAL_PERSON",
        "naturalPersonBeneficiary": {
            "fiscalCode1": "MRLFNC81L04A859L",
            "fiscalCode2": null,
            "fiscalCode3": null,
            "fiscalCode4": null,
            "fiscalCode5": null
        },
        "legalPersonBeneficiary": {
            "fiscalCode": null,
            "legalRepresentativeFiscalCode": null
        }
    }
}'
```

## Endpoint Documentation 

The openApi URL documentation in local environment is `http://localhost:8080/fabrik/api/swagger-ui/index.html`.

## Important gradle tasks
```sh
- `clean`  - clean build results
- `build`  - build public artifact
- `test`   - run unit tests and arch unit tests
- `pitest` - run pitest mutation test
```

## Build
```sh
./gradlew clean build test pitest
```

## Run

```sh
./gradlew bootRun -Dspring.profiles.active=local
```

## License
This Api is distributed under the terms of the MIT License. See the [license](license.md) for details.