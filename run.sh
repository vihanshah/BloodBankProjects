#!/bin/bash

# Blood Bank Database Project - Run Script
# This script compiles and runs the JavaFX application with MySQL connectivity

echo "Blood Bank Database Project"
echo "==========================="
echo

# Set variables - Using local JavaFX SDK
JAVAFX_PATH="javafx-sdk-25.0.1/lib"
MYSQL_CONNECTOR="lib/mysql-connector-j-9.5.0.jar"
SRC_DIR="src"
BIN_DIR="bin"
MAIN_CLASS="application.Main"

# Check if JavaFX SDK exists locally
if [ ! -d "$JAVAFX_PATH" ]; then
    echo "ERROR: JavaFX SDK not found at javafx-sdk-25.0.1"
    echo
    echo "Please download and extract JavaFX SDK 25 from https://openjfx.io/"
    echo "Then extract it to the project root directory as \"javafx-sdk-25.0.1\""
    echo
    exit 1
else
    echo "Using local JavaFX SDK at javafx-sdk-25.0.1"
fi

# Check if MySQL connector exists
if [ ! -f "$MYSQL_CONNECTOR" ]; then
    echo "ERROR: MySQL connector not found at $MYSQL_CONNECTOR"
    echo
    echo "Please download mysql-connector-j-9.5.0.jar and place it in the lib directory"
    echo
    exit 1
fi

# Create bin directory if it doesn't exist
if [ ! -d "$BIN_DIR" ]; then
    echo "Creating bin directory..."
    mkdir -p "$BIN_DIR"
fi

# Compile the Java files
echo "Compiling Java files..."
javac --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml -cp "$MYSQL_CONNECTOR" -d "$BIN_DIR" "$SRC_DIR/application/"*.java

if [ $? -ne 0 ]; then
    echo
    echo "Compilation failed! Please check the error messages above."
    exit 1
fi

echo
echo "Compilation successful!"

# Run the application
echo
echo "Starting the Blood Bank Database application..."
echo
java --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml -cp "$BIN_DIR:$MYSQL_CONNECTOR" $MAIN_CLASS

echo
echo "Application finished."