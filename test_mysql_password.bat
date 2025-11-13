@echo off
setlocal

REM Test different MySQL passwords
set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
set MYSQL_USER=root

echo Testing common MySQL passwords...
echo.

REM Test with no password
echo Testing with no password...
%MYSQL_PATH% -u %MYSQL_USER% -e "SELECT 'Connection successful'" >nul 2>&1
if %errorlevel% equ 0 (
    echo SUCCESS: Connected with no password
    goto :end
)

REM Test with "root" as password
echo Testing with "root" as password...
%MYSQL_PATH% -u %MYSQL_USER% -proot -e "SELECT 'Connection successful'" >nul 2>&1
if %errorlevel% equ 0 (
    echo SUCCESS: Connected with "root" as password
    goto :end
)

REM Test with "password" as password
echo Testing with "password" as password...
%MYSQL_PATH% -u %MYSQL_USER% -ppassword -e "SELECT 'Connection successful'" >nul 2>&1
if %errorlevel% equ 0 (
    echo SUCCESS: Connected with "password" as password
    goto :end
)

REM Test with "admin" as password
echo Testing with "admin" as password...
%MYSQL_PATH% -u %MYSQL_USER% -padmin -e "SELECT 'Connection successful'" >nul 2>&1
if %errorlevel% equ 0 (
    echo SUCCESS: Connected with "admin" as password
    goto :end
)

echo.
echo None of the common passwords worked. Please enter your correct MySQL password manually.
echo.

:end
pause