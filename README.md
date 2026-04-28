# Aplicación: Interpolación por Diferencias Divididas de Newton

## Descripción
Esta es una aplicación interactiva desarrollada en JavaFX que implementa el método de **Interpolación por Diferencias Divididas de Newton** para demostrar cómo funciona este método numérico.

## Características

### 1. **Ingreso de Datos**
   - Agregar puntos (x, y) manualmente
   - Visualizar los puntos en una tabla
   - Eliminar puntos individuales
   - Limpiar todos los datos
   - Cargar ejemplos predefinidos

### 2. **Cálculo de Diferencias Divididas**
   - Genera automáticamente la tabla de diferencias divididas
   - Muestra el proceso paso a paso
   - Calcula los coeficientes del polinomio de Newton

### 3. **Polinomio de Newton**
   - Muestra la forma completa del polinomio
   - Utiliza los coeficientes calculados
   - Formato legible con operaciones detalladas

### 4. **Evaluación del Polinomio**
   - Evalúa el polinomio en cualquier valor de x
   - Muestra el proceso de evaluación paso a paso
   - Resultado con precisión de 6 decimales

### 5. **Análisis de Error**
   - Calcula el error absoluto
   - Calcula el error relativo porcentual
   - Compara con el valor verdadero si se proporciona
   - Útil para validar la precisión de la interpolación

## Cómo Usar

### Paso 1: Agregar Puntos
1. En el campo "x" ingresa el valor de x
2. En el campo "y" ingresa el valor de y
3. Haz clic en "Agregar"
4. Repite para agregar más puntos (mínimo 2 puntos)

### Paso 2: Calcular Diferencias Divididas
1. Una vez tengas al menos 2 puntos, haz clic en "Calcular Diferencias Divididas"
2. Se generará la tabla de diferencias
3. Se mostrará el polinomio de Newton
4. Se mostrará el proceso detallado de cálculo

### Paso 3: Evaluar el Polinomio
1. Ingresa un valor de x en el campo "x ="
2. Haz clic en "Evaluar"
3. Se mostrará el resultado: P(x)
4. El proceso de evaluación se mostrará en el panel de proceso

### Paso 4: Calcular el Error (Opcional)
1. Si conoces el valor verdadero de f(x) en el punto x evaluado
2. Ingresa ese valor en el campo "Valor real ="
3. Haz clic en "Calcular" (en la sección de error)
4. Se mostrarán el error absoluto y relativo

## Ejemplo de Uso

### Datos: f(x) = x²
| x  | y  |
|----|-----|
| 1  | 1   |
| 2  | 4   |
| 3  | 9   |
| 4  | 16  |

1. Agregar los 4 puntos
2. Calcular diferencias divididas
3. El sistema generará: P(x) = 1 + 3(x-1) + 1(x-1)(x-2)
4. Evaluar en x = 2.5: P(2.5) = 6.25
5. Calcular error: Valor real = 6.25, Error = 0%

## Estructura del Código

### Clases Principales

#### `NewtonDividedDifferences.java`
- Implementa el algoritmo de diferencias divididas de Newton
- Calcula la tabla de diferencias
- Evalúa el polinomio
- Calcula errores
- Registra el proceso paso a paso

#### `NewtonInterpolationApp.java`
- Clase principal de la aplicación JavaFX
- Carga la interfaz FXML
- Aplica los estilos CSS

#### `InterpolationController.java`
- Controlador de la interfaz
- Maneja eventos de botones
- Coordina la entrada/salida de datos
- Actualiza la visualización de resultados

### Archivos de Recursos

- **interpolation-view.fxml**: Descripción de la interfaz gráfica
- **styles.css**: Estilos visuales de la aplicación

## Compilación y Ejecución

### Con Maven
```bash
mvn clean javafx:run
```

### Construcción
```bash
mvn clean package
```

## Requisitos
- Java 8 o superior
- JavaFX 21.0.6
- Maven 3.6+

## Fórmula Matemática

El polinomio de Newton se calcula como:

P(x) = f[x₀] + f[x₀,x₁](x-x₀) + f[x₀,x₁,x₂](x-x₀)(x-x₁) + ... + f[x₀,...,xₙ](x-x₀)...(x-xₙ₋₁)

Donde f[x₀,...,xₖ] son los coeficientes de diferencias divididas calculados iterativamente:

f[xᵢ,...,xᵢ₊ₖ] = (f[xᵢ₊₁,...,xᵢ₊ₖ] - f[xᵢ,...,xᵢ₊ₖ₋₁]) / (xᵢ₊ₖ - xᵢ)

## Errores Comunes

1. **"Se necesitan al menos 2 puntos"**: Agrega más datos antes de calcular
2. **"Ya existe un punto con x = ..."**: No puedes tener dos puntos con la misma coordenada x
3. **"Por favor ingresa números válidos"**: Verifica que los campos contengan números

## Ventajas del Método

- Eficiente computacionalmente
- Fácil de extender con nuevos puntos
- Calcula directamente los coeficientes
- Menor sensibilidad a errores de redondeo que Lagrange

## Notas
- Los puntos se ordenan automáticamente por valor de x
- Se pueden usar valores negativos, decimales y muy grandes
- La precisión depende del número de puntos y su distribución
- El margen de error aumenta conforme nos alejamos del rango de datos originales

---

**Desarrollado para**: Curso de Métodos Numéricos - TecNM  
**Fecha**: 2026  
**Versión**: 1.0
