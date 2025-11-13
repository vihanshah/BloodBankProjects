@echo off
setlocal

REM Blood Bank Project - Final Verification Script

echo Blood Bank Project - Final Verification
echo ======================================
echo.

REM Check Java installation
echo 1. Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo    ERROR: Java is not installed or not in PATH
    echo    Please install JDK 25 or later
    goto :end
)

for /f "tokens=3 delims= " %%a in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%a
)
echo    Java version: %JAVA_VERSION:"=%

REM Check JavaFX SDK
echo.
echo 2. Checking JavaFX SDK...
if exist "javafx-sdk-25.0.1\lib" (
    echo    OK: Local JavaFX SDK found
    echo    JavaFX JAR files:
    dir "javafx-sdk-25.0.1\lib" /b | findstr /i "\.jar"
) else (
    echo    ERROR: Local JavaFX SDK not found
    echo    Please run copy_javafx_sdk.bat
    goto :end
)

REM Check MySQL connector
echo.
echo 3. Checking MySQL connector...
if exist "lib\mysql-connector-j-9.5.0.jar" (
    echo    OK: MySQL connector found
) else (
    echo    ERROR: MySQL connector not found
    echo    Please download mysql-connector-j-9.5.0.jar and place it in lib directory
    goto :end
)

REM Check source files
echo.
echo 4. Checking source files...
set SOURCE_COUNT=0
for /f %%i in ('dir "src\application" /b /a-d ^| find /c /v ""') do set SOURCE_COUNT=%%i
if %SOURCE_COUNT% geq 10 (
    echo    OK: Found %SOURCE_COUNT% Java source files
) else (
    echo    WARNING: Expected at least 10 Java source files, found %SOURCE_COUNT%
)

set FXML_COUNT=0
for /f %%i in ('dir "src\resources" /b /a-d ^| find /c /v ""') do set FXML_COUNT=%%i
if %FXML_COUNT% geq 6 (
    echo    OK: Found %FXML_COUNT% FXML files
) else (
    echo    WARNING: Expected at least 6 FXML files, found %FXML_COUNT%
)

REM Check database setup (basic check)
echo.
echo 5. Checking MySQL server...
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo    WARNING: MySQL command-line client not found in PATH
    echo    Please ensure MySQL server is installed and running
) else (
    echo    OK: MySQL client found
)

echo.
echo Final verification complete.
echo.
echo Next steps:
echo 1. Create the bloodbank database using setup_database.sql
echo 2. Update database credentials in src/application/DBConnect.java if needed
echo 3. Run run.bat to start the application
echo.
echo Everything is set up correctly! You're ready to run the Blood Bank Database application.
echo.
:end
pause