#!/bin/bash

# Test Database Connection Script

echo "Testing Database Connection"
echo "=========================="

# Set variables
MYSQL_CONNECTOR="lib/mysql-connector-j-9.5.0.jar"
BIN_DIR="bin"

# Check if required files exist
if [ ! -f "$MYSQL_CONNECTOR" ]; then
    echo "ERROR: MySQL connector not found at $MYSQL_CONNECTOR"
    echo "Please make sure mysql-connector-j-9.5.0.jar is in the lib directory"
    exit 1
fi

if [ ! -d "$BIN_DIR" ]; then
    echo "Creating bin directory..."
    mkdir -p "$BIN_DIR"
fi

# Compile the test class
echo "Compiling TestDBConnection.java..."
javac -cp "$MYSQL_CONNECTOR" -d "$BIN_DIR" "src/application/TestDBConnection.java"

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo "Running database connection test..."
java -cp "$BIN_DIR:$MYSQL_CONNECTOR" application.TestDBConnection