@echo off
setlocal

REM Test Database Connection Script

echo Testing Database Connection
echo ==========================

REM Set variables
set MYSQL_CONNECTOR=lib\mysql-connector-j-9.5.0.jar
set BIN_DIR=bin

REM Check if required files exist
if not exist "%MYSQL_CONNECTOR%" (
    echo ERROR: MySQL connector not found at %MYSQL_CONNECTOR%
    echo Please make sure mysql-connector-j-9.5.0.jar is in the lib directory
    pause
    exit /b 1
)

if not exist "%BIN_DIR%" (
    echo Creating bin directory...
    mkdir "%BIN_DIR%"
)

REM Compile the test class
echo Compiling TestDBConnection.java and DBConnect.java...
javac -cp "%MYSQL_CONNECTOR%" -d "%BIN_DIR%" "src\application\DBConnect.java" "src\application\TestDBConnection.java"

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Running database connection test...
java -cp "%BIN_DIR%;%MYSQL_CONNECTOR%" application.TestDBConnection

pause