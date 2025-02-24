# Spring Boot Application

## Overview
This is a Spring Boot application running on Java 21 that provides functionality for managing medical visits. The application includes endpoints for adding visits and retrieving the last visit of a patient. Additionally, it supports test data generation during startup.

## Features
- **Adding a Visit**: Allows registering a new medical visit.
- **Fetching the Last Visit of a Patient**: Retrieves the most recent visit details.
- **Test Data Generation**: Enables automatic generation of test data in MySQL.

## Endpoints

### 1. Add a Visit
**Endpoint:** `POST /visit/addVisit`

**Required Parameters:**
- `start` (String, ISO 8601 format) – Start time of the visit
- `end` (String, ISO 8601 format) – End time of the visit
- `doctorId` (Long) – ID of the doctor
- `patientId` (Long) – ID of the patient

### 2. Get Patient's Last Visit
**Endpoint:** `GET /patient/getPatientsLastVisit`

**Required Query Parameters:**
- `pageNo` (Integer) – Page number for pagination
- `pageSize` (Integer) – Number of records per page

**Optional Query Parameters:**
- `firstName` (String) – Filter by patient's first name
- `lastName` (String) – Filter by patient's last name
- `doctorId` (Long) – Filter by doctor's ID

*Note:* The data is retrieved using a single request to the database for performance efficiency.

## Test Data Generation
Test data can be generated into the MySQL database during application startup. To enable it, set the following property in `application.properties`:

```
dataGeneration.enabled=true
```

You can also configure the number of records and the date ranges for generated data.

**Database Dumps:**
Prepared database dumps are available in `resources/dump`.

## Requirements
- Java 21
- MySQL Database
- Spring Boot

## Setup & Running the Application
1. Clone the repository.
2. Configure the `application.properties` file with your MySQL database credentials.
3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
4. The application will be available at `http://localhost:8080`.

