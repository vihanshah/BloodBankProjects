# Blood Bank Management System

A comprehensive JavaFX desktop application for managing blood donors with MySQL backend.

## Features

- **Donor Registration**: Register new blood donors with complete information
- **Search Donors**: Search donors by blood group, name, city, or contact
- **Update Donor Info**: Modify existing donor information
- **Delete Donor**: Remove donors from the database
- **Emergency Mode**: Quickly find available donors by blood group
- **Advanced Filter**: Filter donors by city
- **Statistics**: View blood group distribution with pie chart visualization
- **Modern UI**: Enhanced user interface with better styling and organization

## Prerequisites

- Java JDK 8 or higher
- MySQL Server
- JavaFX SDK (included in project)
- MySQL JDBC Driver (included in project)

## Database Setup

1. Create a MySQL database named `bloodbank`:

   ```sql
   CREATE DATABASE bloodbank;
   USE bloodbank;
   ```

2. Create the donors table:

   ```sql
   CREATE TABLE donors (
       donor_id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       blood_group VARCHAR(10) NOT NULL,
       city VARCHAR(50) NOT NULL,
       contact VARCHAR(20) NOT NULL,
       availability BOOLEAN DEFAULT TRUE,
       last_donation_date DATE NOT NULL
   );
   ```

3. Update the database credentials in `src/application/DBConnect.java` if needed:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/bloodbank";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password_here";
   ```

## How to Run

### Method 1: Using Command Line

1. Navigate to the project directory:

   ```
   cd c:\meow\java\BloodBankProject
   ```

2. Compile the Java files:

   ```
   javac --module-path "javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml src\application\*.java
   ```

3. Run the application:
   ```
   java --module-path "javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp src application.Main
   ```

### Method 2: Using the Batch File

1. Double-click on `run.bat` to compile and run the application

## Project Structure

```
BloodBankProject/
├── src/
│   ├── application/
│   │   ├── Main.java              # Entry point
│   │   ├── DBConnect.java         # Database connection
│   │   ├── Donor.java             # Donor model
│   │   ├── DonorDAO.java          # Data access object
│   │   ├── MainMenuController.java # Main menu controller
│   │   ├── RegisterController.java # Registration controller
│   │   ├── SearchController.java   # Search controller
│   │   ├── UpdateController.java   # Update controller
│   │   ├── DeleteController.java   # Delete controller
│   │   ├── EmergencyController.java # Emergency mode controller
│   │   ├── FilterController.java   # Filter controller
│   │   ├── StatisticsController.java # Statistics controller
│   │   └── AboutController.java    # About controller
│   └── resources/
│       ├── MainMenu.fxml          # Main menu UI
│       ├── RegisterDonor.fxml     # Registration UI
│       ├── SearchDonor.fxml       # Search UI
│       ├── UpdateDonor.fxml       # Update UI
│       ├── DeleteDonor.fxml       # Delete UI
│       ├── EmergencyMode.fxml     # Emergency mode UI
│       ├── FilterDonors.fxml      # Filter UI
│       ├── Statistics.fxml        # Statistics UI
│       └── About.fxml             # About UI
├── lib/
│   └── mysql-connector-j-8.0.33.jar # MySQL JDBC driver
├── javafx-sdk-25.0.1/              # JavaFX SDK
├── compile.bat                     # Compilation script
├── run.bat                         # Run script
└── README.md                       # This file
```

## Usage

1. **Register New Donor**: Click "Register New Donor" to add a new donor to the system
2. **Search Donors**: Use the search feature to find donors by various criteria
3. **Update Donor Info**: Select a donor from the search results and click "Update Selected"
4. **Delete Donor**: Select a donor from the search results and click "Delete Selected"
5. **Emergency Mode**: Quickly find available donors by blood group in emergency situations
6. **Advanced Filter**: Filter donors by city
7. **Statistics**: View blood group distribution with pie chart visualization
8. **About**: View information about the application

## Troubleshooting

- **Database Connection Error**: Ensure MySQL is running and the credentials in `DBConnect.java` are correct
- **JavaFX Error**: Ensure the JavaFX SDK path is correct in the run command
- **Classpath Error**: Ensure all required JAR files are in the classpath

## License

This project is for educational purposes only.
