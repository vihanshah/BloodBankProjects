@echo off
setlocal

REM MySQL Password Finder Script

echo MySQL Password Finder
echo ====================
echo.

set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
set MYSQL_USER=root

:password_loop
set /p MYSQL_PASSWORD="Please enter your MySQL password: "

echo Testing password...
%MYSQL_PATH% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -e "SELECT 'Connection successful'" >nul 2>&1

if %errorlevel% equ 0 (
    echo.
    echo SUCCESS: Connected with password: %MYSQL_PASSWORD%
    
    echo.
    echo Updating DBConnect.java with the correct password...
    
    REM Update DBConnect.java with the correct password
    powershell -Command "(gc src\application\DBConnect.java) -replace 'private static final String PASSWORD = \"[^\"]*\";', 'private static final String PASSWORD = \"%MYSQL_PASSWORD%\";' | Out-File -encoding ASCII src\application\DBConnect.java"
    
    echo.
    echo DBConnect.java has been updated with your password.
    
    echo.
    echo Now setting up the bloodbank database...
    
    REM Execute the setup script
    %MYSQL_PATH% -u %MYSQL_USER% -p%MYSQL_PASSWORD% < setup_database.sql
    
    if %errorlevel% equ 0 (
        echo.
        echo Database setup completed successfully!
        echo The bloodbank database and donors table have been created.
        echo.
        echo You can now run the application by double-clicking run.bat
    ) else (
        echo.
        echo ERROR: Database setup failed.
        echo Please check the error messages above.
    )
    
    goto :end
) else (
    echo.
    echo ERROR: Connection failed with password: %MYSQL_PASSWORD%
    echo.
    set /p retry="Do you want to try another password? (y/n): "
    if /i "%retry%"=="y" goto :password_loop
    goto :end
)

:end
echo.
echo Script completed.
pause