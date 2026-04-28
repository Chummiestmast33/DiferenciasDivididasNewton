# 🎯 COMIENZA AQUÍ - Guía de Inicio Rápido

## 👋 ¡Bienvenido!

Has recibido una **aplicación completamente desarrollada** en JavaFX para demostrar el método de Interpolación por Diferencias Divididas de Newton.

## ⚡ Inicio Rápido (3 pasos)

### Paso 1️⃣: Instalar Java 11+
- Ve a: https://adoptium.net/
- Descarga **JDK 21 LTS** (o cualquier versión 11+)
- Instala normalmente
- Verifica con: `java -version`

### Paso 2️⃣: Compilar (solo la primera vez)
```bash
# Windows:
compile.bat

# Linux/Mac:
bash compile.sh
```

### Paso 3️⃣: Ejecutar
```bash
# Windows:
run.bat

# Linux/Mac:
bash run.sh
```

¡Listo! La aplicación debería abrirse en una ventana.

---

## 📚 Documentación

Lee estos archivos en este orden:

1. **INSTALACION.md** ← Si tienes problemas instalando
2. **README.md** ← Características y cómo usar
3. **EJEMPLOS.md** ← Ver ejemplos de uso
4. **ESTRUCTURA.md** ← Entender la arquitectura
5. **CAMBIOS.md** ← Ver qué se creó

---

## 🎨 Características Principales

✨ **Interfaz Profesional**
- Panel de entrada de datos organizado
- Panel de resultados bien estructurado
- Estilos modernos y coherentes

🧮 **Cálculos Precisos**
- Tabla de diferencias divididas
- Polinomio de Newton completo
- Evaluación en cualquier punto
- Cálculo de errores

📊 **Visualización Clara**
- Proceso paso a paso
- Resultados formateados
- Ejemplos predefinidos

---

## 🎓 ¿Qué Puedes Hacer?

### Usuario Final:
1. Ingresar puntos (x, y)
2. Calcular diferencias divididas automáticamente
3. Ver el polinomio de Newton
4. Evaluar el polinomio en cualquier x
5. Calcular error comparando con valor real
6. Entender cada paso del proceso

### Estudiante:
- Aprender el método de Newton
- Experimentar con diferentes datos
- Ver cómo se calculan los coeficientes
- Entender el análisis de errores
- Analizar el código fuente Java

### Profesor:
- Usar como herramienta pedagógica
- Demostrar el método en clase
- Asignar como proyecto de referencia
- Personalizar estilos según necesidad

---

## 📁 Estructura de Carpetas

```
DiferenciasDivididasNewton/
├── src/
│   ├── main/java/
│   │   └── com/starsolutions/
│   │       └── diferenciasdivididasnewton/
│   │           ├── NewtonDividedDifferences.java (lógica)
│   │           ├── NewtonInterpolationApp.java (aplicación)
│   │           └── InterpolationController.java (interfaz)
│   └── main/resources/
│       └── com/starsolutions/
│           └── diferenciasdivididasnewton/
│               ├── interpolation-view.fxml (diseño)
│               └── styles.css (estilos)
├── README.md (guía de uso)
├── INSTALACION.md (cómo instalar)
├── EJEMPLOS.md (ejemplos detallados)
├── ESTRUCTURA.md (arquitectura)
├── CAMBIOS.md (resumen de cambios)
├── compile.bat (compilar Windows)
├── run.bat (ejecutar Windows)
├── compile.sh (compilar Linux/Mac)
└── run.sh (ejecutar Linux/Mac)
```

---

## ❓ Solución Rápida de Problemas

### Error: "java: command not found"
→ Instala Java y asegúrate de que esté en el PATH

### Error: "invalid target release: 11"
→ Tienes Java 8, necesitas Java 11+

### La aplicación no inicia
→ Verifica que compiló sin errores: `mvnw clean compile`

### ¿Más problemas?
→ Lee **INSTALACION.md** para solución detallada

---

## 💡 Ejemplo de Uso

```
1. Agregar puntos: (1, 1), (2, 4), (3, 9)
   ↓
2. Hacer clic: "Calcular Diferencias Divididas"
   ↓
3. Ver resultado: Tabla, polinomio y proceso
   ↓
4. Evaluar en x = 2.5
   ↓
5. Resultado: P(2.5) = 6.25
   ↓
6. Margen de error: 0% (si valor real = 6.25)
```

Ver **EJEMPLOS.md** para ejemplos más detallados.

---

## 🔧 Personalización

El archivo **styles.css** contiene todos los estilos:
- Colores (azul, verde, rojo)
- Fuentes
- Tamaños
- Efectos

Puedes editarlo sin recompilar para cambiar la apariencia.

---

## 📝 Requisitos

- ✅ Java 11 o superior (IMPORTANTE)
- ✅ Maven (incluido con el proyecto)
- ✅ Cualquier sistema operativo (Windows, Linux, macOS)
- ✅ ~300 MB en disco (para descargas)

---

## 📞 Necesitas Ayuda?

1. Leer **INSTALACION.md** - soluciona 90% de problemas
2. Ver **EJEMPLOS.md** - entender cómo usar
3. Revisar el código fuente - bien comentado
4. Buscar en Google el error específico

---

## 🚀 Próximo Paso

👉 **Lee INSTALACION.md** para instrucciones detalladas

O si ya tienes Java 11+:

```bash
compile.bat    # (o bash compile.sh en Mac/Linux)
run.bat        # (o bash run.sh en Mac/Linux)
```

---

**¡Disfruta explorando el Método de Newton! 🎉**

Creado para: Métodos Numéricos - TecNM  
Versión: 1.0  
Estado: Listo para usar ✓
