# 🎓 Aplicación: Interpolación por Diferencias Divididas de Newton

## ✨ ¿Qué se ha creado?

Se ha desarrollado una **aplicación gráfica interactiva en JavaFX** que implementa completamente el método de interpolación por diferencias divididas de Newton. Esta aplicación permite:

### ✅ Características Principales

1. **Ingreso Interactivo de Datos**
   - Agregar puntos (x, y) manualmente
   - Validar que no existan puntos duplicados
   - Tabla visual de los datos
   - Opción de cargar ejemplos predefinidos

2. **Cálculo Automático**
   - Genera la tabla de diferencias divididas paso a paso
   - Calcula los coeficientes del polinomio de Newton
   - Muestra el proceso detallado de cada cálculo

3. **Visualización del Polinomio**
   - Muestra la forma completa del polinomio
   - Formato legible con todos los términos y coeficientes
   - Expresión matemática clara

4. **Evaluación del Polinomio**
   - Evalúa el polinomio en cualquier valor de x
   - Muestra el resultado con 6 decimales de precisión
   - Detalla el proceso de evaluación paso a paso

5. **Análisis de Error**
   - Calcula error absoluto y relativo
   - Compara con el valor verdadero si se proporciona
   - Expresado como porcentaje relativo
   - Útil para validar la precisión de la interpolación

## 📁 Estructura de Archivos Creados

### Java (Clases)
- **`NewtonDividedDifferences.java`**: Implementa el algoritmo matemático
- **`NewtonInterpolationApp.java`**: Clase principal de la aplicación (antes: HelloApplication)
- **`InterpolationController.java`**: Controlador de la interfaz (antes: HelloController)

### Interfaz Gráfica
- **`interpolation-view.fxml`**: Definición de la interfaz visual (antes: hello-view.fxml)
- **`styles.css`**: Estilos visuales profesionales (separados como solicitaste)

### Documentación
- **`README.md`**: Guía completa de uso y características
- **`INSTALACION.md`**: Instrucciones para instalar y ejecutar
- **`ESTRUCTURA.md`**: Este archivo

### Utilidades
- **`compile.bat`**: Script para compilar en Windows
- **`run.bat`**: Script para ejecutar en Windows
- **`compile.sh`**: Script para compilar en Linux/Mac
- **`run.sh`**: Script para ejecutar en Linux/Mac

## 🏗️ Arquitectura

### Clase: NewtonDividedDifferences
Encapsula toda la lógica matemática:
- Cálculo de diferencias divididas
- Evaluación del polinomio
- Cálculo de errores
- Generación de reportes de proceso

### Clase: InterpolationController
Controla la interfaz gráfica:
- Manejo de eventos de botones
- Coordinación de entrada/salida
- Actualización de visualización
- Validación de datos

### Clase: NewtonInterpolationApp
Punto de entrada de la aplicación:
- Carga la interfaz FXML
- Aplica los estilos CSS
- Configura la ventana

## 🎨 Interfaz Gráfica

La interfaz está dividida en dos paneles:

**Panel Izquierdo (Entrada)**
- Tabla para visualizar puntos
- Campos para agregar nuevos puntos
- Botones de control (Agregar, Eliminar, Limpiar)
- Botón para cargar ejemplo
- Campos de evaluación
- Cálculo de errores

**Panel Derecho (Resultados)**
- Tabla de diferencias divididas
- Polinomio de Newton
- Proceso detallado paso a paso
- Todos en áreas de texto editables

## 🎯 Flujo de Uso Típico

```
1. Ingresar datos (mínimo 2 puntos)
   ↓
2. Hacer clic en "Calcular Diferencias Divididas"
   ↓
3. Ver la tabla, polinomio y proceso
   ↓
4. Ingresar un valor de x para evaluar
   ↓
5. Ver el resultado de la evaluación
   ↓
6. (Opcional) Ingresar valor real para calcular error
   ↓
7. Ver margen de error
```

## 📊 Ejemplo de Uso

**Datos**: f(x) = x² en [1, 4]

```
x  | y
---|---
1  | 1
2  | 4
3  | 9
4  | 16
```

**Resultado**: P(x) = 1 + 3(x-1) + 1(x-1)(x-2)

**Evaluación**: P(2.5) = 6.25

**Error**: 0% (si valor real = 6.25)

## 💻 Requisitos Técnicos

- **Java**: 11 o superior (LTS recomendado)
- **Maven**: 3.6+ (incluido con el proyecto)
- **JavaFX**: 21.0.6
- **SO**: Windows, Linux, macOS

## 🚀 Próximos Pasos

1. Instala Java 11+ desde https://adoptium.net/
2. Abre una terminal en el directorio del proyecto
3. Ejecuta:
   - Windows: `compile.bat` y luego `run.bat`
   - Linux/Mac: `bash compile.sh` y `bash run.sh`

## 📚 Temas Educativos Cubiertos

✓ Interpolación polinómica
✓ Método de Newton con diferencias divididas
✓ Análisis de errores
✓ Cálculo iterativo
✓ Programación orientada a objetos
✓ Interfaz gráfica con JavaFX
✓ FXML para diseño de interfaz
✓ CSS para estilos

## 🔧 Personalización

Todos los estilos están en `styles.css`:
- Colores
- Fuentes
- Tamaños
- Espaciado
- Efectos hover

Para personalizar, edita el archivo CSS sin necesidad de recompilar.

## 📝 Notas de Desarrollo

- Código comentado y bien estructurado
- Nombres descriptivos de variables y métodos
- Separación clara entre lógica y presentación
- Manejo de excepciones incluido
- Validación de entrada de datos

## ✅ Checklist de Características

- ✓ Cálculo correcto de diferencias divididas
- ✓ Evaluación precisa del polinomio
- ✓ Cálculo de errores absoluto y relativo
- ✓ Interfaz intuitiva y profesional
- ✓ Estilos separados en CSS
- ✓ Documentación completa
- ✓ Ejemplos predefinidos
- ✓ Scripts de fácil ejecución
- ✓ Tablas visuales editables
- ✓ Procesos detallados mostrados

---

**Creado para**: Curso de Métodos Numéricos - TecNM  
**Fecha**: 2026  
**Versión**: 1.0  
**Estado**: Listo para usar ✓
