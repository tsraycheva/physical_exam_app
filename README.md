# physical_exam_app

Physical_exam is the BE part of an application for storing results from physical examination. It is made for employees in Fire Departments. 
The requirements for the different exercises depend on the gender of the employees and there is conclusion if the specified employee has passed the exam 
or has failed. The application stores the results by year and can produce different types of information.
There are logging and caching implementations.

## Settings

### Docker network and container
Execute:
1. docker network create physical_exam
2. docker compose up -d

### OpenApiDocumentation (Swagger)
http://localhost:8080/swagger-ui/index.html

## The next steps will be creating
1. unit tests for test coverage (in progress). Jacoco test report for test coverage may be generated.
2. postman collection with possible requests
