package com.starsolutions.diferenciasdivididasnewton;

import java.util.ArrayList;
import java.util.List;

public class NewtonDividedDifferences {
    private double[] xValues;
    private double[] yValues;
    private double[][] dividedDifferences;
    private List<String> process;

    public NewtonDividedDifferences(double[] x, double[] y) {
        this.xValues = x;
        this.yValues = y;
        this.process = new ArrayList<>();
        calculateDividedDifferences();
    }

    private void calculateDividedDifferences() {
        int n = xValues.length;
        dividedDifferences = new double[n][n];
        process.clear();

        // Primera columna: valores de y
        for (int i = 0; i < n; i++) {
            dividedDifferences[i][0] = yValues[i];
        }

        process.add("=== PASO 1: Tabla de Diferencias Divididas ===");
        process.add(String.format("Datos iniciales: %d puntos", n));
        process.add("");

        // Calcular las diferencias divididas
        for (int j = 1; j < n; j++) {
            process.add("--- Columna " + (j + 1) + " ---");
            for (int i = 0; i < n - j; i++) {
                double numerator = dividedDifferences[i + 1][j - 1] - dividedDifferences[i][j - 1];
                double denominator = xValues[i + j] - xValues[i];
                dividedDifferences[i][j] = numerator / denominator;

                process.add(String.format("f[x%d,...,x%d] = (%.4f - %.4f) / (%.4f - %.4f) = %.6f",
                        i, i + j, dividedDifferences[i + 1][j - 1], dividedDifferences[i][j - 1],
                        xValues[i + j], xValues[i], dividedDifferences[i][j]));
            }
            process.add("");
        }
    }

    public double evaluate(double x) {
        int n = xValues.length;
        double result = dividedDifferences[0][0];
        double product = 1.0;

        process.add("");
        process.add("=== PASO 2: Evaluación del Polinomio ===");
        process.add(String.format("Evaluando P(%.4f):", x));
        process.add(String.format("P(%.4f) = %.6f", x, result));

        for (int i = 1; i < n; i++) {
            product *= (x - xValues[i - 1]);
            double term = dividedDifferences[0][i] * product;
            result += term;
            process.add(String.format("        + %.6f × ∏(%.4f - x%d) = %.6f",
                    dividedDifferences[0][i], x, i - 1, result));
        }

        return result;
    }

    public double calculateError(double x, double trueValue) {
        double predictedValue = evaluate(x);
        double absoluteError = Math.abs(trueValue - predictedValue);
        double relativeError = Math.abs(absoluteError / trueValue) * 100;

        process.add("");
        process.add("=== PASO 3: Cálculo del Error ===");
        process.add(String.format("Valor predicho:  %.6f", predictedValue));
        process.add(String.format("Valor verdadero: %.6f", trueValue));
        process.add(String.format("Error absoluto:  %.6f", absoluteError));
        process.add(String.format("Error relativo:  %.4f%%", relativeError));

        return relativeError;
    }

    public String getPolynomialString() {
        StringBuilder sb = new StringBuilder();
        int n = xValues.length;

        sb.append("P(x) = ");
        sb.append(String.format("%.6f", dividedDifferences[0][0]));

        for (int i = 1; i < n; i++) {
            sb.append(" + ");
            sb.append(String.format("%.6f", dividedDifferences[0][i]));

            // Agregar los productos (x - x0)(x - x1)...(x - x_{i-1})
            for (int j = 0; j < i; j++) {
                sb.append(String.format("(x - %.4f)", xValues[j]));
            }
        }

        return sb.toString();
    }

    /**
     * Retorna el polinomio en formato LaTeX con fracciones
     */
    public String getPolynomialStringLatex() {
        StringBuilder sb = new StringBuilder();
        int n = xValues.length;

        sb.append("P(x) = ");
        sb.append(String.format("%.6f", dividedDifferences[0][0]));

        for (int i = 1; i < n; i++) {
            sb.append(" + ");
            sb.append(String.format("%.6f", dividedDifferences[0][i]));

            // Agregar los productos (x - x0)(x - x1)...(x - x_{i-1})
            for (int j = 0; j < i; j++) {
                sb.append(String.format("(x - %.4f)", xValues[j]));
            }
        }

        return sb.toString();
    }

    public String getTableAsString() {
        StringBuilder sb = new StringBuilder();
        int n = xValues.length;

        sb.append("x\t\t");
        for (int j = 0; j < n; j++) {
            sb.append("f[");
            for (int k = 0; k <= j; k++) {
                if (k > 0) sb.append(",");
                sb.append("x").append(k);
            }
            sb.append("]\t\t");
        }
        sb.append("\n");

        for (int i = 0; i < n; i++) {
            sb.append(String.format("%.4f\t\t", xValues[i]));
            for (int j = 0; j < n; j++) {
                if (j <= n - i - 1) {
                    sb.append(String.format("%.6f\t", dividedDifferences[i][j]));
                } else {
                    sb.append("-\t\t");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public List<String> getProcessSteps() {
        return process;
    }

    public double[][] getDividedDifferencesTable() {
        return dividedDifferences;
    }
}
