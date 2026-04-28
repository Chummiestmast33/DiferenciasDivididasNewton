@echo off
REM Script para compilar y ejecutar la aplicación con Java 11
REM Si no tienes Java 11, descárgalo desde: https://adoptium.net/

echo.
echo ========================================
echo Verificando versión de Java...
echo ========================================
java -version
echo.

echo Buscando Java 11 o superior en el sistema...
for /f "tokens=*" %%i in ('java -version 2^>^&1 ^| find "version"') do set JAVA_VERSION=%%i
echo Versión encontrada: %JAVA_VERSION%

echo.
echo ========================================
echo Compilando aplicación...
echo ========================================
call mvnw clean compile
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La compilación falló.
    echo Asegúrate de tener Java 11 o superior instalado.
    echo Descárgalo desde: https://adoptium.net/
    pause
    exit /b 1
)

echo.
echo ========================================
echo Compilación completada exitosamente!
echo ========================================
pause
