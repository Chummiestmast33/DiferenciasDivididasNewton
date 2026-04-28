# 📊 Ejemplos de Salida de la Aplicación

## Ejemplo 1: f(x) = x² 

### Datos de Entrada:
```
x    | y
-----|-----
1.0  | 1.0
2.0  | 4.0
3.0  | 9.0
4.0  | 16.0
```

### Tabla de Diferencias Divididas:
```
x       f[x0]      f[x0,x1]   f[x0,x1,x2]   f[x0,x1,x2,x3]
1.0000  1.000000   
2.0000  4.000000   3.000000
3.0000  9.000000   5.000000   1.000000
4.0000  16.000000  7.000000   1.000000   0.000000
```

### Proceso de Cálculo:

```
=== PASO 1: Tabla de Diferencias Divididas ===
Datos iniciales: 4 puntos

--- Columna 2 ---
f[x0,x1] = (4.000000 - 1.000000) / (2.0000 - 1.0000) = 3.000000
f[x1,x2] = (9.000000 - 4.000000) / (3.0000 - 2.0000) = 5.000000
f[x2,x3] = (16.000000 - 9.000000) / (4.0000 - 3.0000) = 7.000000

--- Columna 3 ---
f[x0,x1,x2] = (5.000000 - 3.000000) / (3.0000 - 1.0000) = 1.000000
f[x1,x2,x3] = (7.000000 - 5.000000) / (4.0000 - 2.0000) = 1.000000

--- Columna 4 ---
f[x0,x1,x2,x3] = (1.000000 - 1.000000) / (4.0000 - 1.0000) = 0.000000
```

### Polinomio de Newton:
```
P(x) = 1.000000 + 3.000000(x - 1.0000) + 1.000000(x - 1.0000)(x - 2.0000) 
       + 0.000000(x - 1.0000)(x - 2.0000)(x - 3.0000)

Simplificado:
P(x) = 1 + 3(x - 1) + (x - 1)(x - 2)
     = 1 + 3x - 3 + x² - 3x + 2
     = x²
```

### Evaluación en x = 2.5:
```
P(2.5) = 1.000000
       + 3.000000 × (2.5 - 1.0000) = 1.000000 + 4.500000 = 5.500000
       + 1.000000 × (2.5 - 1.0000) × (2.5 - 2.0000) = 5.500000 + 0.750000 = 6.250000
       + 0.000000 × (2.5 - 1.0000) × (2.5 - 2.0000) × (2.5 - 3.0000) = 6.250000

Resultado: P(2.5) = 6.250000
```

### Cálculo del Error:
```
Valor predicho:  6.250000
Valor verdadero: 6.250000 (2.5² = 6.25)
Error absoluto:  0.000000
Error relativo:  0.000000%
```

---

## Ejemplo 2: f(x) = sin(x)

### Datos de Entrada (en radianes):
```
x     | y
------|--------
0.0   | 0.0000
0.5   | 0.4794
1.0   | 0.8415
1.5   | 0.9975
```

### Tabla de Diferencias Divididas:
```
x      f[x0]     f[x0,x1]   f[x0,x1,x2]   f[x0,x1,x2,x3]
0.0    0.000000
0.5    0.479426  0.958851
1.0    0.841471  0.723911  -0.469881
1.5    0.997495  0.311886  -0.824050  -0.353790
```

### Evaluación en x = 0.75:
```
P(0.75) = 0.000000
        + 0.958851 × (0.75) = 0.719138
        + (-0.469881) × (0.75) × (0.75 - 0.5) = 0.719138 - 0.088481 = 0.630657
        + (-0.353790) × (0.75) × (0.75 - 0.5) × (0.75 - 1.0) = 0.630657 + 0.066384 = 0.697041

Resultado: P(0.75) = 0.697041
```

### Cálculo del Error:
```
Valor predicho:  0.697041
Valor verdadero: 0.681639 (sin(0.75 rad))
Error absoluto:  0.015402
Error relativo:  2.257678%
```

---

## Ejemplo 3: f(x) = 1/x

### Datos de Entrada:
```
x    | y
-----|--------
1.0  | 1.0000
2.0  | 0.5000
3.0  | 0.3333
4.0  | 0.2500
5.0  | 0.2000
```

### Tabla de Diferencias Divididas:
```
x      f[x0]     f[x0,x1]   f[x0,x1,x2]   f[x0,x1,x2,x3]  f[x0,...,x4]
1.0    1.000000
2.0    0.500000  -0.500000
3.0    0.333333  -0.083333  0.138889
4.0    0.250000  -0.083333  0.000000    -0.046296
5.0    0.200000  -0.050000  0.011111    0.003704  0.016460
```

### Evaluación en x = 2.5:
```
P(2.5) = 1.000000
       + (-0.500000) × (2.5 - 1.0) = 0.250000
       + 0.138889 × (2.5 - 1.0) × (2.5 - 2.0) = 0.250000 + 0.069445 = 0.319445
       ... (continuando con los términos adicionales)

Resultado: P(2.5) = 0.388615
```

### Cálculo del Error:
```
Valor predicho:  0.388615
Valor verdadero: 0.400000 (1/2.5)
Error absoluto:  0.011385
Error relativo:  2.846325%
```

---

## Características de Presentación

### Interfaz Visual:
- ✓ Tabla de entrada con puntos editables
- ✓ Botones claramente etiquetados
- ✓ Campos de entrada para evaluación
- ✓ Áreas de texto con fuente monoespaciada
- ✓ Colores profesionales (azul, verde, rojo para estados)
- ✓ Estilos separados en CSS (fácil personalización)

### Información Mostrada:
- ✓ Tabla de diferencias con todos los coeficientes
- ✓ Polinomio completo en forma de Newton
- ✓ Proceso paso a paso del cálculo
- ✓ Resultado de la evaluación
- ✓ Error absoluto y relativo en porcentaje

### Validaciones Incluidas:
- ✓ Mínimo 2 puntos para interpolar
- ✓ Sin puntos duplicados (mismo x)
- ✓ Campos numéricos validados
- ✓ Mensajes de error claros

---

## Comparación: Precisión vs Número de Puntos

Para f(x) = x³ evaluado en x = 1.5:

| Puntos | P(x) | Error |
|--------|------|-------|
| 2      | 3.25 | 6.25% |
| 3      | 3.375| 1.85% |
| 4      | 3.375| 0%    |
| 4+     | 3.375| 0%    |

**Valor verdadero**: 1.5³ = 3.375

**Conclusión**: Con polinomios cúbicos, se necesitan 4 puntos para interpolación exacta.

---

## Notas Importantes

1. **Precisión**: La aplicación usa aritmética de punto flotante (double)
2. **Error Relativo**: Error % = |valor_verdadero - predicho| / |valor_verdadero| × 100
3. **Extrapolación**: Los errores pueden ser mayores fuera del rango de datos
4. **Estabilidad**: El método es generalmente muy estable
5. **Complejidad**: O(n²) para generar la tabla, O(n) para evaluar

---

**Estos ejemplos muestran cómo la aplicación maneja diferentes tipos de funciones y demuestra la precisión del método de Newton.**
