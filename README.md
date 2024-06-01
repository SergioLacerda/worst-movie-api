# worst-producer-api

## Introduction
All this is about TEXO IT challenge.
This application will return the worst producer by comparison of intervals of prizes.

## Built With
JAVA 19
Spring Boot 3.3.0
Gradle
H2 database memory

## Build and Test
```
./gradlew clean build
```
## Run application
```
./gradlew bootRun -Dserver.port=8080
```

## CSV movie file
By default, will be loaded in the path:
```
./src/main/resources/data/movies.csv
```
But you can override this path loading this environment variable: *ENV_CSV_FILE*

Example:
```
ENV_CSV_FILE=./home/csv/newPathMovies.csv
```

## Rest API
Can be acessed by URL:
```
http://localhost:8080/api/v1/producer
```