
---

# Animal Shelter Management System 🐾
A desktop application built with JavaFX for managing animal adoptions, shelters, and adopters, backed by a MySQL database. This is a mini-project for our Object-Oriented Programming with Java (OOPJ) course.

---

## Table of Contents
- [About The Project](#about-the-project)
- [Features](#features)
- [Built With](#built-with)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation & Setup](#installation--setup)
- [Database Schema](#database-schema)
- [Project Team](#project-team)

---

## About The Project

This application provides a user-friendly graphical interface (GUI) to interact with an animal shelter database. It allows users to view information about animals available for adoption, the shelters that house them, the people who adopt them, and the history of all adoptions. It serves as a practical demonstration of JavaFX for front-end development connected to a MySQL back-end, following Object-Oriented principles.

---

## Features
- **View Animals:** See a complete list of all animals, including their type, breed, age, and health status.
- **View Shelters:** Display all registered shelters and their locations.
- **View Adopters:** List all individuals who have registered to adopt, including contact information.
- **View Adoption Records:** See a full history of all successful adoptions.
- **Refresh Data:** A "Refresh" button on the UI allows fetching the latest data from the database without restarting the application.

---

## Built With
This project was built using the following technologies:
*   [Java](https://www.java.com/) - The core programming language.
*   [JavaFX](https://openjfx.io/) - Used for creating the graphical user interface.
*   [MySQL](https://www.mysql.com/) - The relational database management system.
*   [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) - The JDBC driver for database connectivity.
*   [Visual Studio Code](https://code.visualstudio.com/) - The primary code editor.
*   [Git & GitHub](https://github.com/) - For version control and collaboration.

---

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

You must have the following software installed on your machine:
*   **JDK (Java Development Kit)** - Version 17 or higher is recommended.
*   **JavaFX SDK** - Download from [GluonHQ](https://gluonhq.com/products/javafx/). Do **not** commit this to the repository.
*   **MySQL Server & MySQL Workbench** - Download from the official [MySQL website](https://dev.mysql.com/downloads/).

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/beeth73/Animal_Shelter_DB_OOPJ_MiniProject.git
    ```

2.  **Set up the database:**
    *   Open MySQL Workbench and connect to your local server.
    *   Create a new SQL script file named `create_database.sql` and paste the contents of the database creation script into it. Run this script to create the tables.
    *   Create another SQL script file named `populate_database.sql` and paste the contents of the data insertion script into it. Run this script to fill the tables with sample data.

3.  **Configure the Java Project:**
    *   **Add the JDBC Driver:** Download the **MySQL Connector/J** (`.jar` file) and place it inside the `/lib` folder in the project directory. (Note: The `/lib` folder is intentionally ignored by Git).
    *   **Update Connection Details:** Open the `src/DatabaseConnection.java` file and update the `dbPassword` variable with your own MySQL root password.
    *   **Configure VS Code:** Open the `.vscode/launch.json` file. You must update the `vmArgs` path to point to the exact location of the `lib` folder within your downloaded JavaFX SDK. For example:
      ```json
      "vmArgs": "--module-path \"C:/path/to/your/javafx-sdk-21/lib\" --add-modules javafx.controls,javafx.fxml"
      ```

4.  **Run the application:**
    *   Open the project in VS Code.
    *   Navigate to the "Run and Debug" panel.
    *   Select "Launch Animal Adoption App" from the dropdown and click the green play button.

---

## Database Schema
Our database consists of four main tables with relationships established to ensure data integrity.

```sql
-- SHELTER Table
CREATE TABLE `SHELTER` (
  `Sid` INT NOT NULL AUTO_INCREMENT,
  `Sname` VARCHAR(100) NOT NULL,
  `loc` VARCHAR(255) NULL,
  PRIMARY KEY (`Sid`)
);

-- ANIMAL Table
CREATE TABLE `ANIMAL` (
  `Aid` INT NOT NULL AUTO_INCREMENT,
  `health_status` VARCHAR(255) NULL,
  `Type` VARCHAR(50) NOT NULL,
  `Breed` VARCHAR(50) NULL,
  `age` INT NULL,
  `ShelterId` INT NULL,
  PRIMARY KEY (`Aid`),
  FOREIGN KEY (`ShelterId`) REFERENCES `SHELTER` (`Sid`)
);

-- ADOPTER Table
CREATE TABLE `ADOPTER` (
  `Adid` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  `Add` VARCHAR(255) NULL,
  `Email` VARCHAR(100) NULL UNIQUE,
  `Phone_No` VARCHAR(20) NULL,
  PRIMARY KEY (`Adid`)
);

-- ADOPTIONS Table
CREATE TABLE `ADOPTIONS` (
  `AnimalId` INT NOT NULL,
  `AdopterId` INT NOT NULL,
  `Adoption_Date` DATE NULL,
  `fee` DECIMAL(10, 2) NULL,
  PRIMARY KEY (`AnimalId`, `AdopterId`),
  FOREIGN KEY (`AnimalId`) REFERENCES `ANIMAL` (`Aid`),
  FOREIGN KEY (`AdopterId`) REFERENCES `ADOPTER` (`Adid`)
);
```

---

## Project Team
This project was developed by:
*   **Bhushan** - [beeth73](https://github.com/beeth73)
*   **Allan Almeida** - [AllanDaGreat](https://github.com/AllanDaGreat)
*   **Zain Desai** - [zaindesai07](https://github.com/zaindesai07)
