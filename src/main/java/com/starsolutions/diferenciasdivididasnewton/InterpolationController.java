package com.starsolutions.diferenciasdivididasnewton;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InterpolationController {
    @FXML private TableView<Point> dataTable;
    @FXML private TableColumn<Point, Double> xColumn;
    @FXML private TableColumn<Point, Double> yColumn;
    @FXML private TextField xEvalField;
    @FXML private TextField trueValueField;
    @FXML private Label resultLabel;
    @FXML private Label errorLabel;
    @FXML private TextField xPointField;
    @FXML private TextField yPointField;
    @FXML private ComboBox<Integer> pointsCountCombo;

    // Nueva tabla real para diferencias
    @FXML private TableView<DifferenceRow> differencesTable;

    // Contenedor para proceso con LaTeX
    @FXML private VBox processContainer;

    // Área de polinomio
    @FXML private TextArea polynomialArea;

    private ObservableList<Point> dataPoints = FXCollections.observableArrayList();
    private ObservableList<DifferenceRow> differenceRows = FXCollections.observableArrayList();
    private NewtonDividedDifferences solver;

    @FXML
    public void initialize() {
        // Configurar tabla de datos de entrada
        xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty().asObject());
        dataTable.setItems(dataPoints);

        // Configurar tabla de diferencias (se llenará dinámicamente)
        differencesTable.setItems(differenceRows);

        // Configurar combobox
        ObservableList<Integer> pointCounts = FXCollections.observableArrayList(2, 3, 4, 5, 6, 7, 8);
        pointsCountCombo.setItems(pointCounts);
        pointsCountCombo.setValue(3);
    }

    @FXML
    private void onAddPoint() {
        try {
            double x = Double.parseDouble(xPointField.getText());
            double y = Double.parseDouble(yPointField.getText());

            for (Point p : dataPoints) {
                if (Math.abs(p.getX() - x) < 1e-10) {
                    showAlert("Error", "Ya existe un punto con x = " + x);
                    return;
                }
            }

            dataPoints.add(new Point(x, y));
            dataPoints.sort((p1, p2) -> Double.compare(p1.getX(), p2.getX()));

            xPointField.clear();
            yPointField.clear();

            // Actualizar opciones del ComboBox
            updateComboBoxOptions();
        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor ingresa números válidos");
        }
    }

    @FXML
    private void onRemovePoint() {
        Point selected = dataTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dataPoints.remove(selected);
            // Actualizar opciones del ComboBox
            updateComboBoxOptions();
        } else {
            showAlert("Advertencia", "Por favor selecciona un punto para eliminar");
        }
    }

    @FXML
    private void onClearData() {
        dataPoints.clear();
        // Actualizar opciones del ComboBox
        updateComboBoxOptions();
        clearResults();
    }

    @FXML
    private void onGenerateExample() {
        dataPoints.clear();
        double[][] example = {
                {1.0, 1.0},
                {2.0, 4.0},
                {3.0, 9.0},
                {4.0, 16.0}
        };

        for (double[] point : example) {
            dataPoints.add(new Point(point[0], point[1]));
        }

        // Actualizar opciones del ComboBox
        updateComboBoxOptions();
    }

    @FXML
    private void onCalculate() {
        if (dataPoints.size() < 2) {
            showAlert("Error", "Se necesitan al menos 2 puntos para interpolar");
            return;
        }

        try {
            double[] xValues = new double[dataPoints.size()];
            double[] yValues = new double[dataPoints.size()];

            for (int i = 0; i < dataPoints.size(); i++) {
                xValues[i] = dataPoints.get(i).getX();
                yValues[i] = dataPoints.get(i).getY();
            }

            solver = new NewtonDividedDifferences(xValues, yValues);

            // Llenar tabla de diferencias REAL
            populateDifferencesTable(xValues, solver.getDividedDifferencesTable());

            // Mostrar polinomio (solo forma original en LaTeX)
            polynomialArea.setText(solver.getPolynomialString());
            polynomialArea.setStyle("-fx-font-size: 14; -fx-control-inner-background: #fff8f8;");

            // Renderizar proceso con LaTeX
            renderProcessWithLatex(solver.getProcessSteps(), xValues);

            showAlert("Éxito", "Diferencias divididas calculadas correctamente");
        } catch (Exception e) {
            showAlert("Error", "Error al calcular: " + e.getMessage());
        }
    }

    /**
     * Llena la tabla real con los datos de diferencias divididas
     */
    private void populateDifferencesTable(double[] xValues, double[][] differences) {
        differenceRows.clear();
        int n = xValues.length;

        // Crear filas - cada fila i corresponde al punto i
        for (int i = 0; i < n; i++) {
            DifferenceRow row = new DifferenceRow(xValues[i], n);

            // Para cada fila i, asignar los valores correctos:
            // Columna j debe contener differences[i-j][j]
            // Es decir, la diferencia que INCLUYE el punto i
            for (int j = 0; j < n; j++) {
                // differences[row][col] donde row = i-j, col = j
                int diffRow = i - j;

                if (diffRow >= 0 && diffRow < n && j < n) {
                    // Verificar que el valor tenga sentido
                    if (differences[diffRow][j] != Double.NaN) {
                        row.setDifference(j, differences[diffRow][j]);
                    }
                }
            }

            differenceRows.add(row);
        }

        // Crear columnas dinámicamente
        differencesTable.getColumns().clear();

        // Columna X
        TableColumn<DifferenceRow, Double> xCol = new TableColumn<>("x");
        xCol.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        xCol.setPrefWidth(80);
        differencesTable.getColumns().add(xCol);

        // Columnas de diferencias - calcular ancho dinámicamente
        int numColumns = n;
        double columnWidth = Math.max(90, (differencesTable.getPrefWidth() - 100) / numColumns);

        for (int j = 0; j < numColumns; j++) {
            final int colIndex = j;
            TableColumn<DifferenceRow, Double> col = new TableColumn<>("f[x₀,...,x" + j + "]");
            col.setCellValueFactory(cellData -> {
                DoubleProperty prop = cellData.getValue().differenceProperty(colIndex);
                if (prop == null) {
                    return new javafx.beans.property.ReadOnlyObjectWrapper<>(Double.NaN);
                }
                return prop.asObject();
            });
            col.setPrefWidth(columnWidth);
            col.setStyle("-fx-alignment: CENTER-RIGHT;");

            // Formatear las celdas para mostrar los valores correctamente
            col.setCellFactory(column -> new javafx.scene.control.TableCell<DifferenceRow, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || Double.isNaN(item)) {
                        setText("");
                    } else {
                        setText(String.format("%.6f", item));
                    }
                }
            });

            differencesTable.getColumns().add(col);
        }

        // Permitir que la tabla se expanda
        differencesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Renderiza el proceso con LaTeX
     */
    private void renderProcessWithLatex(java.util.List<String> processSteps, double[] xValues) {
        processContainer.getChildren().clear();

        for (String step : processSteps) {
            if (step.trim().isEmpty()) {
                // Espacio en blanco
                processContainer.getChildren().add(new Separator());
            } else if (step.contains("===")) {
                // Título principal (PASO X)
                Label title = new Label(step.replace("===", "").trim());
                title.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #2c3e50; -fx-padding: 10 0 5 0;");
                title.setWrapText(true);
                processContainer.getChildren().add(title);
            } else if (step.contains("---")) {
                // Subtítulo (Columna X)
                Label subtitle = new Label(step.replace("---", "").trim());
                subtitle.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #34495e; -fx-padding: 8 0 4 0;");
                subtitle.setWrapText(true);
                processContainer.getChildren().add(subtitle);
            } else if (step.contains("f[") || step.contains("=") || step.contains("/")) {
                // Convertir a LaTeX y renderizar
                String latex = convertStepToLatex(step);
                try {
                    ImageView latexImage = LaTeXRenderer.renderLatex(latex);
                    latexImage.setStyle("-fx-padding: 5;");
                    processContainer.getChildren().add(latexImage);
                } catch (Exception e) {
                    // Fallback: mostrar como texto
                    Label label = new Label(step);
                    label.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 11;");
                    label.setWrapText(true);
                    processContainer.getChildren().add(label);
                }
            } else {
                // Texto normal
                Label label = new Label(step);
                label.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 11; -fx-text-fill: #34495e;");
                label.setWrapText(true);
                processContainer.getChildren().add(label);
            }
        }
    }

    /**
     * Convierte un paso del proceso a LaTeX con fracciones
     */
    private String convertStepToLatex(String step) {
        String latex = step;

        // Convertir divisiones típicas "a / b" a fracciones LaTeX "\frac{a}{b}"
        // Patrón: número.número / número.número
        latex = latex.replaceAll("(\\d+\\.?\\d*) / (\\d+\\.?\\d*)", "\\\\frac{$1}{$2}");

        // También convertir números sin decimales
        latex = latex.replaceAll("(\\d+) / (\\d+)", "\\\\frac{$1}{$2}");

        // Convertir notación de diferencias divididas
        latex = latex.replaceAll("f\\[(.*?)\\]", "f[$1]");

        return latex;
    }

    @FXML
    private void onEvaluate() {
        if (solver == null) {
            showAlert("Advertencia", "Primero calcula las diferencias divididas");
            return;
        }

        try {
            double x = Double.parseDouble(xEvalField.getText());
            double result = solver.evaluate(x);

            resultLabel.setText(FormulaFormatter.formatEvaluationResult(x, result));
            resultLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 16; -fx-font-family: 'Courier New';");
        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor ingresa un número válido para x");
        }
    }

    @FXML
    private void onCalculateError() {
        if (solver == null) {
            showAlert("Advertencia", "Primero calcula las diferencias divididas");
            return;
        }

        try {
            double x = Double.parseDouble(xEvalField.getText());
            double trueValue = Double.parseDouble(trueValueField.getText());
            double predicted = solver.evaluate(x);
            double error = solver.calculateError(x, trueValue);

            errorLabel.setText(FormulaFormatter.formatErrorReadable(
                    Math.abs(trueValue - predicted),
                    error));
            errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12; -fx-font-family: 'Courier New';");
        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor ingresa números válidos");
        }
    }

    @FXML
    private void onClearResults() {
        clearResults();
    }

    private void clearResults() {
        resultLabel.setText("Resultado: --");
        errorLabel.setText("Error: --");
        processContainer.getChildren().clear();
        polynomialArea.clear();
        differenceRows.clear();
        solver = null;
    }

    /**
     * Actualiza dinámicamente las opciones del ComboBox basado en la cantidad de puntos
     */
    private void updateComboBoxOptions() {
        int currentPointCount = dataPoints.size();

        // Crear lista de opciones desde 2 hasta la cantidad actual de puntos
        ObservableList<Integer> validCounts = FXCollections.observableArrayList();
        for (int i = 2; i <= currentPointCount; i++) {
            validCounts.add(i);
        }

        // Si no hay opciones válidas, mostrar opciones por defecto
        if (validCounts.isEmpty()) {
            validCounts = FXCollections.observableArrayList(2, 3, 4, 5, 6, 7, 8);
        }

        pointsCountCombo.setItems(validCounts);

        // Si el valor actual seleccionado es mayor que la cantidad de puntos, ajustarlo
        Integer currentValue = pointsCountCombo.getValue();
        if (currentValue == null || currentValue > currentPointCount) {
            pointsCountCombo.setValue(validCounts.get(validCounts.size() - 1));
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clase interna para representar un punto
     */
    public static class Point {
        private javafx.beans.property.DoubleProperty x = new javafx.beans.property.SimpleDoubleProperty();
        private javafx.beans.property.DoubleProperty y = new javafx.beans.property.SimpleDoubleProperty();

        public Point(double x, double y) {
            this.x.set(x);
            this.y.set(y);
        }

        public double getX() {
            return x.get();
        }

        public double getY() {
            return y.get();
        }

        public javafx.beans.property.DoubleProperty xProperty() {
            return x;
        }

        public javafx.beans.property.DoubleProperty yProperty() {
            return y;
        }
    }
}
