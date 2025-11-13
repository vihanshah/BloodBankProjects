@echo off
setlocal

REM Blood Bank Database Setup Script
REM Uses the correct MySQL path

echo Blood Bank Database Setup
echo =======================
echo.

set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

REM Check if MySQL is available at the specified path
if not exist %MYSQL_PATH% (
    echo ERROR: MySQL not found at %MYSQL_PATH%
    echo.
    echo Please ensure MySQL Server 8.0 is installed at the expected location
    pause
    exit /b 1
)

echo MySQL found at %MYSQL_PATH%
echo.

REM Get MySQL credentials from user
set /p MYSQL_USER="Enter MySQL username (default: root): " || set MYSQL_USER=root
set /p MYSQL_PASSWORD="Enter MySQL password (press Enter for no password): "

echo.
echo Setting up bloodbank database...
echo.

REM Execute the setup script
if "%MYSQL_PASSWORD%"=="" (
    %MYSQL_PATH% -u %MYSQL_USER% < setup_database.sql
) else (
    %MYSQL_PATH% -u %MYSQL_USER% -p%MYSQL_PASSWORD% < setup_database.sql
)

if %errorlevel% equ 0 (
    echo.
    echo Database setup completed successfully!
    echo The bloodbank database and donors table have been created.
) else (
    echo.
    echo ERROR: Database setup failed.
    echo Please check the error messages above.
)

echo.
echo Next steps:
echo 1. Update database credentials in src/application/DBConnect.java:
echo    - Open the file in a text editor
echo    - Change USER to "%MYSQL_USER%"
echo    - Change PASSWORD to your actual MySQL password
echo 2. Run run.bat to start the application
echo.
pause