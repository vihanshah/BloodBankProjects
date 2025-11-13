@echo off
setlocal

REM Setup Blood Bank Database
set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
set MYSQL_USER=root
set MYSQL_PASSWORD=Yuganshi9.

echo Setting up Blood Bank Database...
echo.

%MYSQL_PATH% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -e "CREATE DATABASE IF NOT EXISTS bloodbank;"
if %errorlevel% neq 0 (
    echo ERROR: Failed to create database
    pause
    exit /b 1
)

echo Database 'bloodbank' created successfully.
echo.

%MYSQL_PATH% -u %MYSQL_USER% -p%MYSQL_PASSWORD% bloodbank -e "CREATE TABLE IF NOT EXISTS donors (donor_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), blood_group VARCHAR(5), city VARCHAR(50), contact VARCHAR(15), availability BOOLEAN, last_donation_date DATE);"
if %errorlevel% neq 0 (
    echo ERROR: Failed to create table
    pause
    exit /b 1
)

echo Table 'donors' created successfully.
echo.

%MYSQL_PATH% -u %MYSQL_USER% -p%MYSQL_PASSWORD% bloodbank -e "INSERT INTO donors (name, blood_group, city, contact, availability, last_donation_date) VALUES ('John Doe', 'O+', 'New York', '1234567890', true, '2025-10-15'), ('Jane Smith', 'A-', 'Los Angeles', '0987654321', true, '2025-09-20'), ('Robert Johnson', 'B+', 'Chicago', '1122334455', false, '2025-08-10'), ('Emily Davis', 'AB-', 'Houston', '6677889900', true, '2025-10-01'), ('Michael Wilson', 'O-', 'Phoenix', '5544332211', false, '2025-07-25');"
if %errorlevel% neq 0 (
    echo ERROR: Failed to insert sample data
    pause
    exit /b 1
)

echo Sample data inserted successfully.
echo.

echo Blood Bank Database setup completed successfully!
echo.
echo You can now run the application by double-clicking run.bat
echo.
pause