@echo off
REM Script para ejecutar la aplicación

echo.
echo ========================================
echo Ejecutando Aplicación...
echo Interpolación por Diferencias Divididas de Newton
echo ========================================
echo.

call mvnw javafx:run

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: No se pudo ejecutar la aplicación.
    echo Asegúrate de haber compilado primero ejecutando: compile.bat
    pause
    exit /b 1
)
