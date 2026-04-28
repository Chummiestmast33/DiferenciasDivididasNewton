# 📋 Guía de Instalación y Ejecución

## ⚠️ Requisito Importante

Esta aplicación requiere **Java 11 o superior**. Actualmente tienes Java 8 instalado.

## 1️⃣ Instalar Java 11 o Superior

### Opción A: Adoptium (Recomendado)
1. Ve a: https://adoptium.net/
2. Descarga **JDK 21 LTS** (o cualquier versión 11+)
3. Ejecuta el instalador
4. Asegúrate de que **"Set JAVA_HOME variable"** esté seleccionado
5. Completa la instalación

### Opción B: Oracle Java
1. Ve a: https://www.oracle.com/java/technologies/downloads/
2. Descarga **JDK 21** (o cualquier versión 11+)
3. Ejecuta el instalador
4. Sigue los pasos de instalación

## 2️⃣ Verificar la Instalación

Abre una terminal/CMD y ejecuta:

```bash
java -version
javac -version
```

Deberías ver algo como:
```
openjdk version "21" 2023-09-19 LTS
```

## 3️⃣ Compilar la Aplicación

### En Windows:
```bash
compile.bat
```

### En Linux/Mac:
```bash
bash compile.sh
```

O manualmente:
```bash
./mvnw clean compile
```

## 4️⃣ Ejecutar la Aplicación

### En Windows:
```bash
run.bat
```

O manualmente:
```bash
./mvnw javafx:run
```

### En Linux/Mac:
```bash
bash run.sh
```

O manualmente:
```bash
./mvnw javafx:run
```

## 5️⃣ Solución de Problemas

### Error: "java: command not found"
- Java no está en el PATH
- Instala Java nuevamente y asegúrate de que la opción de agregar al PATH esté seleccionada
- Reinicia la terminal después de instalar

### Error: "invalid target release: 11"
- Tienes Java 8, necesitas Java 11 o superior
- Desinstala Java 8 e instala Java 11+
- Reinicia la terminal después de instalar

### Error al compilar con Maven
- Ejecuta: `./mvnw clean`
- Luego: `./mvnw compile`

### La aplicación no inicia
- Verifica que compiló sin errores
- Prueba ejecutar desde la línea de comandos para ver el error exacto

## 📝 Alternativa: Usar un IDE

Si no quieres usar la terminal, puedes usar un IDE como:

1. **IntelliJ IDEA Community Edition** (Gratuito)
   - Abre el proyecto
   - Ir a Build → Build Project
   - Ir a Run → Run 'NewtonInterpolationApp'

2. **Eclipse** (Gratuito)
   - Abre el proyecto
   - Click derecho → Run As → Maven Build...
   - Escribe: `javafx:run`

3. **VS Code** (Gratuito)
   - Instala extension "Extension Pack for Java"
   - Abre el proyecto
   - Usa la terminal integrada para ejecutar los comandos

## 🎯 Si Todo Funciona

Deberías ver una ventana con la interfaz de la aplicación:
- Panel de entrada de datos a la izquierda
- Panel de resultados a la derecha
- Campos para ingresar puntos (x, y)
- Botones para calcular y evaluar

## 📚 Documentación Completa

Ver el archivo `README.md` para documentación completa de la aplicación.
