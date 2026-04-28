package com.starsolutions.diferenciasdivididasnewton;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Utilidad para renderizar fórmulas LaTeX en JavaFX
 */
public class LaTeXRenderer {
    private static final float FONT_SIZE = 20f;
    private static final int DPI = 150;

    /**
     * Convierte una fórmula LaTeX en una ImageView de JavaFX
     */
    public static ImageView renderLatex(String latexFormula) {
        try {
            // Crear fórmula LaTeX
            TeXFormula formula = new TeXFormula(latexFormula);

            // Convertir a icono con el tamaño deseado
            TeXIcon icon = formula.new TeXIconBuilder()
                    .setStyle(TeXConstants.STYLE_DISPLAY)
                    .setSize(FONT_SIZE)
                    .build();

            // Crear imagen BufferedImage
            BufferedImage bufferedImage = new BufferedImage(
                    icon.getIconWidth(),
                    icon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            // Dibujar el icono en la imagen
            icon.paintIcon(new JPanel(), bufferedImage.getGraphics(), 0, 0);

            // Convertir BufferedImage a Image de JavaFX
            Image image = convertToFXImage(bufferedImage);

            // Crear ImageView
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            return imageView;
        } catch (Exception e) {
            System.err.println("Error renderizando LaTeX: " + latexFormula);
            return new ImageView();
        }
    }

    /**
     * Crea un contenedor con la fórmula LaTeX renderizada
     */
    public static StackPane createLatexContainer(String latexFormula) {
        StackPane container = new StackPane();
        container.setPrefHeight(60);
        container.setStyle("-fx-border-color: #d0d0d0; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #fafafa;");

        try {
            ImageView imageView = renderLatex(latexFormula);
            container.getChildren().add(imageView);
        } catch (Exception e) {
            // Fallback: mostrar texto plano si falla LaTeX
            javafx.scene.control.Label label = new javafx.scene.control.Label(latexFormula);
            label.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 11;");
            container.getChildren().add(label);
        }

        return container;
    }

    /**
     * Convierte BufferedImage a Image de JavaFX
     */
    private static Image convertToFXImage(BufferedImage bufferedImage) {
        return javafx.embed.swing.SwingFXUtils.toFXImage(bufferedImage, null);
    }

    /**
     * Formatea el polinomio de Newton en LaTeX
     */
    public static String formatPolynomialToLatex(double[] coefficients, double[] xValues) {
        StringBuilder latex = new StringBuilder();
        latex.append("P(x) = ");

        for (int i = 0; i < coefficients.length; i++) {
            if (i > 0) {
                latex.append(" + ");
            }

            // Coeficiente
            latex.append(String.format("%.4f", coefficients[i]));

            // Productos
            if (i > 0) {
                for (int j = 0; j < i; j++) {
                    latex.append(String.format("(x - %.2f)", xValues[j]));
                }
            }

            if (i < coefficients.length - 1) {
                latex.append(" \\\\");
            }
        }

        return latex.toString();
    }

    /**
     * Renderiza la fórmula de error
     */
    public static String formatErrorToLatex(double absolute, double relative) {
        return String.format(
                "\\text{Error Absoluto: } %.6f \\\\[0.5em] \\text{Error Relativo: } %.4f\\%%",
                absolute, relative
        );
    }

    /**
     * Renderiza la tabla de diferencias en texto plano mejorado
     */
    public static String formatDifferencesTableToLatex(double[][] table, double[] xValues) {
        StringBuilder latex = new StringBuilder();
        latex.append("\\begin{array}{");

        // Encabezados de columna
        for (int i = 0; i < table[0].length; i++) {
            latex.append("c|");
        }
        latex.append("}\n");

        // Filas
        for (int i = 0; i < table.length; i++) {
            latex.append(String.format("x_{%d}=%.2f", i, xValues[i]));
            for (int j = 0; j < Math.min(i + 1, table[i].length); j++) {
                latex.append(String.format(" & %.4f", table[i][j]));
            }
            latex.append(" \\\\ \n");
        }

        latex.append("\\end{array}");
        return latex.toString();
    }
}
