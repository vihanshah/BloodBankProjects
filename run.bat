@echo off
echo Compiling Blood Bank Management System...
javac --module-path "javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp "lib/mysql-connector-j-9.5.0.jar;src" src/application/*.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Compilation successful!
echo Starting Blood Bank Management System...
java --module-path "javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp "lib/mysql-connector-j-9.5.0.jar;src" application.Main
pause