#!/bin/bash
# Script para compilar y ejecutar la aplicación con Java 11

echo ""
echo "========================================"
echo "Verificando versión de Java..."
echo "========================================"
java -version
echo ""

echo "Compilando aplicación..."
echo "========================================"
./mvnw clean compile

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: La compilación falló."
    echo "Asegúrate de tener Java 11 o superior instalado."
    echo "Descárgalo desde: https://adoptium.net/"
    exit 1
fi

echo ""
echo "========================================"
echo "Compilación completada exitosamente!"
echo "========================================"
