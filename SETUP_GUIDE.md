# Blood Bank Database Project - Setup Guide

This guide will help you set up and run the Blood Bank Database project on your system.

## Prerequisites

1. Java Development Kit (JDK 25 or later)
2. MySQL Server
3. JavaFX SDK 25 (already installed at C:\javafx-sdk-25.0.1)

## Step-by-Step Setup Instructions

### STEP 1: Verify JavaFX SDK Installation

You've already installed JavaFX SDK 25 at:

```
C:\javafx-sdk-25.0.1
```

This is the correct location, and our scripts have been updated to use this path.

### STEP 2: Set Up MySQL Database

1. Make sure MySQL Server is running

2. Create the database and tables using the provided SQL script:

   Open your MySQL client (MySQL Workbench, phpMyAdmin, or command line) and execute:

   ```sql
   -- Create the bloodbank database
   CREATE DATABASE IF NOT EXISTS bloodbank;

   -- Use the bloodbank database
   USE bloodbank;

   -- Create the donors table
   CREATE TABLE IF NOT EXISTS donors (
     donor_id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(50),
     blood_group VARCHAR(5),
     city VARCHAR(50),
     contact VARCHAR(15),
     availability BOOLEAN,
     last_donation_date DATE
   );
   ```

3. Update the database credentials in `src/application/DBConnect.java` if needed:
   ```java
   private static final String USER = "root"; // Change as per your MySQL setup
   private static final String PASSWORD = "root"; // Change as per your MySQL setup
   ```

### STEP 3: Verify Project Structure

Your project should have the following structure:

```
BloodBankProject/
│
├── lib/
│   └── mysql-connector-j-9.5.0.jar
│
├── src/
│   ├── application/
│   │   ├── Main.java
│   │   ├── DBConnect.java
│   │   ├── Donor.java
│   │   ├── DonorDAO.java
│   │   ├── MainMenuController.java
│   │   ├── RegisterController.java
│   │   ├── SearchController.java
│   │   ├── UpdateController.java
│   │   ├── EmergencyController.java
│   │   └── FilterController.java
│   │
│   └── resources/
│       ├── MainMenu.fxml
│       ├── RegisterDonor.fxml
│       ├── SearchDonor.fxml
│       ├── UpdateDonor.fxml
│       ├── EmergencyMode.fxml
│       └── FilterDonor.fxml
│
├── .vscode/
│   ├── launch.json
│   └── settings.json
│
├── run.bat (Windows)
├── run.sh (Linux/Mac)
├── setup_database.sql
└── README.md
```

### STEP 4: Run the Application

#### Option 1: Using the provided scripts (Recommended)

**Windows:**
Double-click [run.bat](file:///c%3A/meow/java/BloodBankProject/run.bat) or run from command prompt:

```
run.bat
```

**Linux/Mac:**
Make the script executable and run:

```
chmod +x run.sh
./run.sh
```

#### Option 2: Manual compilation and execution

**For Windows:**

```cmd
java --module-path "C:\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp ".;lib/mysql-connector-j-9.5.0.jar;bin" application.Main
```

**For Linux/Mac:**

```bash
java --module-path "C:/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml -cp ".:lib/mysql-connector-j-9.5.0.jar:bin" application.Main
```

### STEP 5: Verify Output

When you run the application:

1. A JavaFX window opens titled "Blood Bank Database"
2. You'll see a main menu with options:
   - Register Donor
   - Search Donors
   - Emergency Mode
   - Filter Donors
3. You can register new donors, search for existing ones, and view available donors in emergency mode

## Troubleshooting

### Common Issues:

1. **JavaFX SDK not found**: Make sure you've downloaded and extracted the JavaFX SDK to C:\javafx-sdk-25.0.1

2. **MySQL connection failed**:

   - Verify MySQL server is running
   - Check database credentials in [DBConnect.java](file:///c%3A/meow/java/BloodBankProject/src/application/DBConnect.java)
   - Ensure the `bloodbank` database exists

3. **ClassNotFoundException**: Make sure all required JAR files are in the classpath

4. **Java version issues**: Ensure you're using JDK 25 or later

## Project Components

### Java Classes:

- **Main.java**: Application entry point
- **DBConnect.java**: Database connection handler
- **Donor.java**: Donor model class
- **DonorDAO.java**: Data Access Object for CRUD operations
- **Controllers**: Handle UI logic for each screen

### FXML Files:

- **MainMenu.fxml**: Main navigation screen
- **RegisterDonor.fxml**: Donor registration form
- **SearchDonor.fxml**: Donor search and listing
- **UpdateDonor.fxml**: Donor update form
- **EmergencyMode.fxml**: Emergency donor view
- **FilterDonor.fxml**: Advanced filtering interface

## Features

1. **Register new donors** with name, blood group, city, contact, availability, and last donation date
2. **Search donors** by blood group
3. **Filter donors** by city or availability
4. **Edit (update)** donor information
5. **Delete donor** entries
6. **Emergency mode** to view all currently available donors by group
7. **TableView** to display results
8. **Data persistence** using MySQL + JDBC
9. **Alerts** for success/error messages
10. **Sorting and filtering** options in the table

Enjoy using the Blood Bank Database application!
