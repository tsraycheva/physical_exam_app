# physical_exam_app

Physical_exam is the BE part of an application for storing results from physical examination. It is made for employees in Fire Departments. 
The requirements for the different exercises depend on the gender of the employees and there is conclusion if the specified employee has passed the exam 
or has failed. The application stores the results by year and can produce different types of information.
There are logging and caching implementations.
The application development is in progress.

## Settings

### Docker network and container
Execute:
1. docker network create physical_exam
2. docker compose up -d

### OpenApiDocumentation (Swagger)
To run Swagger go to address http://localhost:8080/swagger-ui/index.html

### Test report
A JaCoCo is applied to the project. After executing test from Maven Lifecycle, the report could be found at **/physical_exam/target/site/jacoco/index.html**

## The next step will be
1. Implementing Spring Security (in progress)
2. Exception handling
