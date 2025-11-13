# Test MySQL Connection
Write-Host "Testing MySQL Connection..."
Write-Host "======================"
Write-Host

# Test with no password
Write-Host "Testing with no password..."
try {
    & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -e "SELECT 'Connection successful'" 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "SUCCESS: Connected with no password"
        exit 0
    }
} catch {
    # Continue to next test
}

# Test with "root" as password
Write-Host "Testing with 'root' as password..."
try {
    & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -proot -e "SELECT 'Connection successful'" 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "SUCCESS: Connected with 'root' as password"
        exit 0
    }
} catch {
    # Continue to next test
}

# Test with "password" as password
Write-Host "Testing with 'password' as password..."
try {
    & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -ppassword -e "SELECT 'Connection successful'" 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "SUCCESS: Connected with 'password' as password"
        exit 0
    }
} catch {
    # Continue to next test
}

# Test with "admin" as password
Write-Host "Testing with 'admin' as password..."
try {
    & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -padmin -e "SELECT 'Connection successful'" 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "SUCCESS: Connected with 'admin' as password"
        exit 0
    }
} catch {
    # Continue to next test
}

Write-Host
Write-Host "None of the common passwords worked."
Write-Host "Please check your MySQL installation and credentials."