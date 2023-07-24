# Migros Courier Case

## Used Technologies

**Java 17, H2 database ( H2 database has been used in the project to avoid any additional requirements for running the application. ), spring boot 3.1.2, maven, swagger.**

## Design Patterns

**Dependency Injection**

**Builder**

**Singleton**

## How To Run

**You can choose one of the two methods to run the project..**

1. Clone the project.

```bash
  git clone https://github.com/emintiryaki/migros-courier-case.git
```

Go to the project directory and run the project with the following command.

```bash
  mvn spring-boot:run
```

2. You can use the couriercase-0.0.1-SNAPSHOT.jar file located in the 'target' directory within the project. After downloading the file, navigate to its location and run the project with the following command.

```bash
  java -jar couriercase-0.0.1-SNAPSHOT.jar
```

Regardless of which of the two methods mentioned above is used, requests can be made via Swagger or Postman.

**Swagger :** http://localhost:8100/swagger-ui/index.html

**Postman :** The Postman collection is included within the project.

## API Usage

#### Get all courier

```http
  GET /courier/get-all-couriers
```

#### Get the total travel distance of the courier based on the courier id.

```http
  GET /courier/get-total-travel-distance/{courierId}
```

| Parametre   | Type      |
| :---------- | :-------- |
| `courierId` | `Integer` |

#### Change courier location and log if any courier matching any store.

This API takes a list of request objects, allowing the location information of multiple couriers to be processed.

```http
  Post /courier/change-courier-location
```

| Parametre   | Type                                                |
| :---------- | :-------------------------------------------------- |
| `courierId` | `Integer`                                           |
| `lat`       | `Double`                                            |
| `lng`       | `Double`                                            |
| `time`      | `LocalDateTime` (Time Format : yyyy-MM-dd HH:mm:ss) |
