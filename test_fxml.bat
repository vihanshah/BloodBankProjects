@echo off
setlocal

REM FXML Test Script

echo FXML Test
echo =========

REM Set variables
set JAVAFX_PATH=javafx-sdk-25.0.1\lib
set MYSQL_CONNECTOR=lib\mysql-connector-j-9.5.0.jar
set BIN_DIR=bin

echo Compiling FXMLTest.java...
javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp "%MYSQL_CONNECTOR%" -d "%BIN_DIR%" "src\application\FXMLTest.java"

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Running FXML test...
java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp "%BIN_DIR%;%MYSQL_CONNECTOR%" application.FXMLTest

pause