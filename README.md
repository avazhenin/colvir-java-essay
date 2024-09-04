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
docker-compose --file docker-compose.yml -p xmlcalendar up  --build -d --remove-orphans
```

### Useful commands 

* To run dev services such as database and message broker ( RabbitMQ )

```bash 
docker-compose --file xmlcalendar-dev-services.yml -p xmlcalendar-dev-services up --build -d --remove-orphans
```

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

### Calendar REST API

This Spring Boot application provides a REST API for accessing calendar-related data, including public holidays and workday information for various years and languages.

#### Endpoints

1. **Get Calendar XML**
  - **URL:** `/calendar`
  - **Method:** `GET`
  - **Description:** Retrieves the calendar data in XML format for the specified year and language.
  - **Query Parameters:**
    - `language`: The language code (e.g., `en`, `ru`, `kz`).
    - `year`: The year for which the calendar data is requested.
    - **Response:**
      - `200 OK` with the calendar XML data if available.
      - `404 Not Found` if the calendar data is not found.

2. **Force Update**
  - **URL:** `/calendar/forceUpdate`
  - **Method:** `POST`
  - **Description:** Triggers a manual update of the calendar data asynchronously. This is useful if you need to synchronize calendar information on demand.
  - **Response:**
    - `202 Accepted` when the update process is started.

3. **Get Date Information**
  - **URL:** `/calendar/getDateInfo`
  - **Method:** `GET`
  - **Description:** Retrieves detailed information about a specific date, including whether it's a workday or holiday, and the type of day.
  - **Query Parameters:**
    - `date`: The date in `DD.MM.YYYY` format (e.g., `25.12.2024`).
    - `country`: The country code for which the calendar data is relevant (e.g., `US`, `KZ`).
  - **Response:**
    - `200 OK` with detailed information about the date.
    - `404 Not Found` if the date information is not available.

4. **Get Last Updated Information**
  - **URL:** `/calendar/lastUpdated`
  - **Method:** `GET`
  - **Description:** Retrieves the timestamp of the last calendar data update.
  - **Response:**
    - `200 OK` with the last update timestamp.
    - `500 Internal Server Error` if no data is available.

5. **Get Calendar Statistics**
  - **URL:** `/calendar/statistic`
  - **Method:** `GET`
  - **Description:** Provides statistics for the specified year, including the number of workdays and holidays.
  - **Query Parameters:**
    - `year`: The year for which statistics are requested (e.g., `2024`).
  - **Response:**
    - `200 OK` with the statistics data, including the number of workdays and holidays.
    - `500 Internal Server Error` if the year parameter is missing or invalid.

### Example CURL Commands

You can use the following `curl` commands to interact with the Calendar REST API on port `8880`.

```bash
# 1. Get Calendar XML
curl -X GET "http://localhost:8880/calendar?language=kz&year=2023"
```
```bash
# 2. Force Calendar Update
curl -X POST "http://localhost:8880/calendar/forceUpdate"
```
```bash
# 3. Get Date Information
curl -X GET "http://localhost:8880/calendar/getDateInfo?date=01.01.2023&country=kz"
```
```bash
# 4. Get Last Updated Information
curl -X GET "http://localhost:8880/calendar/lastUpdated"
```
```bash
# 5. Get Calendar Statistics
curl -X GET "http://localhost:8880/calendar/statistic?year=2024"
```
