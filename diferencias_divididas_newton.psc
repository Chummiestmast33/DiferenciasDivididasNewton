Algoritmo DiferenciasDivididasNewton
    Definir n, i, j Como Entero
    Definir xEvaluar, resultado, producto, termino, numerador, denominador Como Real
    
    Escribir "Ingrese el numero de puntos de datos (n): "
    Leer n
    
    Dimension xValues[n]
    Dimension yValues[n]
    Dimension diferenciasDivididas[n, n]
    
    Escribir ""
    Escribir "--- Ingrese los valores de x e y ---"
    Para i <- 1 Hasta n Hacer
        Escribir "x[", i, "]: "
        Leer xValues[i]
        Escribir "y[", i, "]: "
        Leer yValues[i]
        diferenciasDivididas[i, 1] <- yValues[i]
    FinPara
    
    // Calculo de las diferencias divididas
    Para j <- 2 Hasta n Hacer
        Para i <- 1 Hasta n - j + 1 Hacer
            numerador <- diferenciasDivididas[i + 1, j - 1] - diferenciasDivididas[i, j - 1]
            denominador <- xValues[i + j - 1] - xValues[i]
            diferenciasDivididas[i, j] <- numerador / denominador
        FinPara
    FinPara
    
    Escribir ""
    Escribir "--- Tabla de Diferencias Divididas ---"
    Para i <- 1 Hasta n Hacer
        Escribir Sin Saltar xValues[i], "    | "
        Para j <- 1 Hasta n - i + 1 Hacer
            Escribir Sin Saltar diferenciasDivididas[i, j], "    | "
        FinPara
        Escribir ""
    FinPara
    
    Escribir ""
    Escribir "Ingrese el valor de x a evaluar: "
    Leer xEvaluar
    
    // Evaluacion del polinomio
    resultado <- diferenciasDivididas[1, 1]
    producto <- 1.0
    
    Para i <- 2 Hasta n Hacer
        producto <- producto * (xEvaluar - xValues[i - 1])
        termino <- diferenciasDivididas[1, i] * producto
        resultado <- resultado + termino
    FinPara
    
    Escribir ""
    Escribir "El valor interpolado P(", xEvaluar, ") es: ", resultado
    
FinAlgoritmo
