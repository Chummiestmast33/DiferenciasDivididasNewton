package com.starsolutions.diferenciasdivididasnewton;

/**
 * Formateador para mejorar la presentación visual de fórmulas y números
 */
public class FormulaFormatter {

    /**
     * Formatea un número con símbolos Unicode matemáticos
     */
    public static String formatNumber(double value) {
        return String.format("%.6f", value);
    }

    /**
     * Convierte la fórmula de Newton a formato legible con Unicode
     */
    public static String formatPolynomialReadable(double[] coefficients, double[] xValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("P(x) = ");

        for (int i = 0; i < coefficients.length; i++) {
            if (i > 0 && coefficients[i] >= 0) {
                sb.append(" + ");
            } else if (i > 0) {
                sb.append(" ");
            }

            sb.append(String.format("%.6f", coefficients[i]));

            if (i > 0) {
                for (int j = 0; j < i; j++) {
                    sb.append(String.format(" × (x − %.4f)", xValues[j]));
                }
            }
        }

        return sb.toString();
    }

    /**
     * Formatea la tabla de diferencias con separadores Unicode
     */
    public static String formatDifferencesTableReadable(double[][] table, double[] xValues) {
        StringBuilder sb = new StringBuilder();

        sb.append("┌─────────┬");
        for (int j = 1; j < table[0].length; j++) {
            sb.append("─────────────┬");
        }
        sb.append("┐\n");

        sb.append("│ x\t\t│");
        for (int j = 1; j < table[0].length; j++) {
            sb.append(String.format(" f[x₀,...,x%d]\t│", j));
        }
        sb.append("\n");

        sb.append("├─────────┼");
        for (int j = 1; j < table[0].length; j++) {
            sb.append("─────────────┼");
        }
        sb.append("┤\n");

        for (int i = 0; i < table.length; i++) {
            sb.append(String.format("│ %.4f\t│", xValues[i]));
            for (int j = 0; j < Math.min(i + 1, table[i].length); j++) {
                sb.append(String.format(" %.10f\t│", table[i][j]));
            }
            sb.append("\n");
        }

        sb.append("└─────────┴");
        for (int j = 1; j < table[0].length; j++) {
            sb.append("─────────────┴");
        }
        sb.append("┘");

        return sb.toString();
    }

    /**
     * Formatea el resultado de la evaluación
     */
    public static String formatEvaluationResult(double x, double result) {
        return String.format("P(%.4f) = %.6f", x, result);
    }

    /**
     * Formatea el error con símbolos matemáticos
     */
    public static String formatErrorReadable(double absolute, double relative) {
        StringBuilder sb = new StringBuilder();
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append(String.format("│ Error Absoluto:  |Ey| = %.6f\n", absolute));
        sb.append(String.format("│ Error Relativo:  εᵣ = %.6f%%\n", relative));
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        return sb.toString();
    }

    /**
     * Convierte coeficientes a notación científica si es necesario
     */
    public static String toScientificNotation(double value) {
        if (Math.abs(value) < 0.0001 && value != 0) {
            return String.format("%.3e", value);
        }
        return String.format("%.6f", value);
    }

    /**
     * Formatea con subíndices Unicode
     */
    public static String formatWithSubscripts(String base, int index) {
        String[] subscripts = {"₀", "₁", "₂", "₃", "₄", "₅", "₆", "₇", "₈", "₉"};
        return base + subscripts[Math.min(index, 9)];
    }

    /**
     * Crea una tabla visual con bordes
     */
    public static String createFormattedTable(String[][] data, String[] headers) {
        StringBuilder sb = new StringBuilder();

        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                widths[i] = Math.max(widths[i], row[i].length());
            }
        }

        sb.append("┌");
        for (int i = 0; i < headers.length; i++) {
            for (int j = 0; j < widths[i] + 2; j++) sb.append("─");
            if (i < headers.length - 1) sb.append("┬");
        }
        sb.append("┐\n");

        sb.append("│");
        for (int i = 0; i < headers.length; i++) {
            sb.append(" ").append(String.format("%-" + widths[i] + "s", headers[i])).append(" │");
        }
        sb.append("\n");

        sb.append("├");
        for (int i = 0; i < headers.length; i++) {
            for (int j = 0; j < widths[i] + 2; j++) sb.append("─");
            if (i < headers.length - 1) sb.append("┼");
        }
        sb.append("┤\n");

        for (String[] row : data) {
            sb.append("│");
            for (int i = 0; i < row.length; i++) {
                sb.append(" ").append(String.format("%-" + widths[i] + "s", row[i])).append(" │");
            }
            sb.append("\n");
        }

        sb.append("└");
        for (int i = 0; i < headers.length; i++) {
            for (int j = 0; j < widths[i] + 2; j++) sb.append("─");
            if (i < headers.length - 1) sb.append("┴");
        }
        sb.append("┘");

        return sb.toString();
    }
}
