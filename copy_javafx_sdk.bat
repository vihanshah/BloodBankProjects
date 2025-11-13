@echo off
setlocal

REM Copy JavaFX SDK to project directory
echo Copying JavaFX SDK to project directory...

if exist "C:\javafx-sdk-25.0.1" (
    echo Found JavaFX SDK at C:\javafx-sdk-25.0.1
    echo Copying to project directory...
    
    REM Remove existing javafx-sdk-25.0.1 directory if it exists
    if exist "javafx-sdk-25.0.1" (
        echo Removing existing javafx-sdk-25.0.1 directory...
        rmdir /s /q "javafx-sdk-25.0.1"
    )
    
    REM Copy the entire directory
    xcopy "C:\javafx-sdk-25.0.1" "javafx-sdk-25.0.1\" /E /I /H /Y
    
    if %errorlevel% equ 0 (
        echo JavaFX SDK successfully copied to project directory!
        echo.
        echo Note: The application will now use the local copy at javafx-sdk-25.0.1
        echo You can delete C:\javafx-sdk-25.0.1 if you no longer need it elsewhere.
    ) else (
        echo ERROR: Failed to copy JavaFX SDK to project directory.
    )
) else (
    echo ERROR: JavaFX SDK not found at C:\javafx-sdk-25.0.1
    echo Please make sure JavaFX SDK 25 is installed at this location.
)

pause