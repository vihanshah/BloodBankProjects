@echo off
setlocal

REM Test JavaFX Application Script

echo Testing JavaFX Application
echo =======================

REM Set variables
set JAVAFX_PATH=javafx-sdk-25.0.1\lib
set BIN_DIR=bin

REM Check if JavaFX SDK exists
if not exist "%JAVAFX_PATH%" (
    echo ERROR: JavaFX SDK not found at javafx-sdk-25.0.1
    echo.
    echo Please run copy_javafx_sdk.bat to copy the SDK to the project directory
    echo OR download and extract JavaFX SDK 25 from https://openjfx.io/
    echo.
    pause
    exit /b 1
) else (
    echo Using local JavaFX SDK at javafx-sdk-25.0.1
)

REM Create bin directory if it doesn't exist
if not exist "%BIN_DIR%" (
    echo Creating bin directory...
    mkdir "%BIN_DIR%"
)

REM Compile the test application
echo Compiling TestApp.java...
javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -d "%BIN_DIR%" "src\application\TestApp.java"

if %errorlevel% neq 0 (
    echo.
    echo Compilation failed! Please check the error messages above.
    pause
    exit /b %errorlevel%
)

echo.
echo Compilation successful!

REM Run the test application
echo.
echo Starting the JavaFX test application...
echo.
java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp "%BIN_DIR%" application.TestApp

echo.
echo Test application finished.
pause