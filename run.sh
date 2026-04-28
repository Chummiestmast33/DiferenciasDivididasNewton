#!/bin/bash
# Script para ejecutar la aplicación

echo ""
echo "========================================"
echo "Ejecutando Aplicación..."
echo "Interpolación por Diferencias Divididas de Newton"
echo "========================================"
echo ""

./mvnw javafx:run

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: No se pudo ejecutar la aplicación."
    echo "Asegúrate de haber compilado primero ejecutando: ./compile.sh"
    exit 1
fi
