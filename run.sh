#!/usr/bin/env sh

# Build the application
gradle build

echo ""

# Run the application
java -classpath ./app/build/classes/java/main tech.dario.swissre.charlenescoffeecorner.Main
