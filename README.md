### Project Information
This project was completed as part of an advanced Java course focused on mastering the Spring Boot framework.

### Project Goal
The goal is to master the Spring Boot framework and become familiar with related technologies such as message brokers, databases, and authorization mechanisms.

### Functional Requirements
* The service must update data according to the period specified in the settings file.
* A broker message must notify users when there are changes in the calendar data.
* The service must provide the following REST API endpoints:
    * _Synchronize on demand_
    * _Retrieve the last update information [date, time, and result]_
    * _Get information for a requested date_
    * _Retrieve work or holiday information for a requested year_

### Components
This project utilizes the following components:
* **Loading Calendar Service** - A REST client service that fetches the required calendar information.
* **RabbitMQ** - Facilitates communication in a microservice architecture.
* **PostgreSQL Database** - Handles data CRUD operations.

### Features
* **Simultaneous Calendar Updates** - Calendar update requests for each year are processed in parallel.

### Additional Requirements
A Docker Compose file is provided to run the application in a Docker environment. Use the following command to start Docker Compose and set up the necessary environment:

```bash 
docker-compose up --build -d --remove-orphans
```

### Useful commands 

* To build project

```bash 
mvn clean install
```

* To build project without running tests

```bash
mvn clean install -DskipTests=true
```

* To run spring boot app
```bash
mvn spring-boot:run
```
