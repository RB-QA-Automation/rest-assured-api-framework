# Restful Booker - API Test Automation Framework

This project is a robust API test automation framework built from scratch in Java to test the [Restful Booker API](https://restful-booker.herokuapp.com/). It provides a full end-to-end testing solution covering all major CRUD operations and incorporates industry-standard best practices for automation.

---

## 🚀 Features

- **Full CRUD Coverage:** Automated tests for Create, Read, Update, and Delete (CRUD) operations on bookings.
- **Automated Authentication:** A `BaseTest` class handles token generation and management automatically before tests run.
- **Professional HTML Reporting:** Generates detailed, shareable HTML test reports using **ExtentReports**, configured via a TestNG listener.
- **Structured Logging:** Uses **Log4j2** for clear, color-coded console output and automatically writes detailed logs to a file (`app.log`).
- **External Configuration:** Manages the base URI and credentials in an external `config.properties` file, allowing tests to run against different environments without code changes.
- **API Client Layer:** A clean separation of concerns between test logic (`BookingTests`) and API interaction logic (`BookingClient`), making the framework highly maintainable and readable.
- **POJO-Based Data Management:** Uses Plain Old Java Objects (POJOs) for type-safe and reusable test data.
- **End-to-End Test Flows:** Uses TestNG's `dependsOnMethods` to create realistic user scenarios (e.g., Create → Update → Delete).

---

## 🛠️ Technologies Used

- **Language:** Java
- **API Testing:** REST Assured
- **Test Runner:** TestNG
- **Build & Dependency Management:** Maven
- **Reporting:** ExtentReports
- **Logging:** Log4j2

---

## ▶️ How to Run

1.  Clone the repository to your local machine.
2.  Ensure you have Java and Maven installed.
3.  You can run the entire test suite from the command line using Maven:
    ```bash
    mvn clean test
    ```
4.  Alternatively, import the project into an IDE like Eclipse and run the `testng.xml` file as a TestNG Suite.
