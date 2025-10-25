#!/bin/bash

# University Management System - Run Script

echo "======================================"
echo "University Management System"
echo "======================================"
echo ""

# Check if bin directory exists
if [ ! -d "bin" ]; then
    echo "Compiling Java files..."
    mkdir -p bin
    javac -d bin -sourcepath src $(find src -name "*.java")
    
    if [ $? -eq 0 ]; then
        echo "✓ Compilation successful"
    else
        echo "✗ Compilation failed"
        exit 1
    fi
else
    echo "Using existing compiled classes in bin/"
fi

echo ""
echo "Starting application..."
echo "Test accounts:"
echo "  Faculty: prof1/password or prof2/password"
echo "  Student: student1/password or student2/password"
echo ""

# Run the application
java -cp bin info5100.university.example.UI.MainFrame

