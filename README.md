# weather-api-service

* I've developed a backend solution that provides weather information for a specific pincode and date, storing the data efficiently and optimizing future requests. The system fetches latitude and longitude based on the pincode and retrieves weather details using external APIs. It’s built using Spring Boot, Hibernate, and PostgreSQL, ensuring effective storage, retrieval, and management of weather data, while minimizing redundant API calls.


## Technology Stack:

## Java, Maven, Spring Boot, Hibernate: 
* I chose Java as the programming language due to its robustness and widespread usage. Spring Boot simplifies the setup of Spring-based applications, while Hibernate facilitates object-relational mapping (ORM), streamlining database interactions.

## PostgreSQL Database Integration:
* Utilized as the database to store the weather data, pincode details (latitude and longitude), and weather information. PostgreSQL offers strong data integrity, reliability, and support for geospatial data, making it suitable for this use case.

## Application Setup Instructions
 * Clone the repository to your local machine:
  > git clone https://github.com/Mahii-12/weather-api-service.git
 * Build the project using Maven:
  > mvn clean install

## External APIs:
* OpenWeather API: Used to fetch weather information based on latitude and longitude.
* Nominatim API (OpenStreetMap): Used to convert the pincode into latitude and longitude coordinates, optimizing the process of fetching weather data.

## Entity Structure:
## Entities:

* Created entities to represent pincode coordinates and weather data.

* ZipCodeCoordinates: Stores pincode, latitude, and longitude information.
* ZipWeatherData: Stores weather details such as temperature, wind speed, precipitation, and other weather-related information for a specific date and pincode.

##  Optimized API Calls:
## API Optimization:
* Implemented logic to avoid redundant external API calls. If weather information for a particular pincode and date already exists in the database, the API fetches it from the database instead of making a new call to the external weather API.

* Pincode to Coordinates: When a pincode is provided, the Nominatim API converts it to latitude and longitude, which is stored in the database for future queries.
* Weather Data: Based on the latitude and longitude, the OpenWeather API retrieves the weather information, which is then saved in the database for future requests.

## Scalability and SOLID Principles:

* SOLID Principles:
> By adhering to SOLID principles, I've designed a system that is modular, maintainable, and easily extensible, laying the foundation for scalability and future enhancements.


## Testing:

* JUnit and Mockito:
  > Tested the controller endpoints using JUnit and Mockito, where JUnit is a widely-used testing framework for Java applications, while Mockito is a popular mocking framework. Together, they allow for thorough testing of controller methods and REST APIs, ensuring correct behavior and identifying potential issues early in the development cycle.

## Postman Validation:

* Functional Testing:
  > Used Postman to enables functional testing of REST APIs by sending requests and validating responses. This ensures that endpoints function correctly and meet specified requirements, enhancing system reliability and user confidence.
