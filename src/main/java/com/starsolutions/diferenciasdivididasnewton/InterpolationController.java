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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

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

    @FXML private TableView<DifferenceRow> differencesTable;

    @FXML private VBox processContainer;

    @FXML private TextArea polynomialArea;

    @FXML private LineChart<Number, Number> interpolationChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;

    private ObservableList<Point> dataPoints = FXCollections.observableArrayList();
    private ObservableList<DifferenceRow> differenceRows = FXCollections.observableArrayList();
    private NewtonDividedDifferences solver;

    @FXML
    public void initialize() {
        xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty().asObject());
        dataTable.setItems(dataPoints);

        differencesTable.setItems(differenceRows);

        ObservableList<Integer> pointCounts = FXCollections.observableArrayList(2, 3, 4, 5, 6, 7, 8);
        pointsCountCombo.setItems(pointCounts);
        pointsCountCombo.setValue(3);
        
        setupChartInteraction();
    }

    private void setupChartInteraction() {
        if (interpolationChart == null) return;
        
        final double[] dragStart = new double[2];

        interpolationChart.setOnMousePressed(evt -> {
            dragStart[0] = evt.getX();
            dragStart[1] = evt.getY();
        });

        interpolationChart.setOnMouseDragged(evt -> {
            double scaleX = (xAxis.getUpperBound() - xAxis.getLowerBound()) / interpolationChart.getWidth();
            double scaleY = (yAxis.getUpperBound() - yAxis.getLowerBound()) / interpolationChart.getHeight();

            double dx = (evt.getX() - dragStart[0]) * scaleX;
            double dy = (evt.getY() - dragStart[1]) * scaleY;

            xAxis.setLowerBound(xAxis.getLowerBound() - dx);
            xAxis.setUpperBound(xAxis.getUpperBound() - dx);

            yAxis.setLowerBound(yAxis.getLowerBound() + dy);
            yAxis.setUpperBound(yAxis.getUpperBound() + dy);

            dragStart[0] = evt.getX();
            dragStart[1] = evt.getY();
        });

        interpolationChart.setOnScroll(evt -> {
            double zoomFactor = evt.getDeltaY() > 0 ? 0.9 : 1.1;
            
            double currentXRange = xAxis.getUpperBound() - xAxis.getLowerBound();
            if (zoomFactor > 1 && currentXRange > 100000) return;
            if (zoomFactor < 1 && currentXRange < 0.001) return;

            double newXRange = (xAxis.getUpperBound() - xAxis.getLowerBound()) * zoomFactor;
            double newYRange = (yAxis.getUpperBound() - yAxis.getLowerBound()) * zoomFactor;

            double xMid = (xAxis.getLowerBound() + xAxis.getUpperBound()) / 2;
            double yMid = (yAxis.getLowerBound() + yAxis.getUpperBound()) / 2;

            xAxis.setLowerBound(xMid - newXRange / 2);
            xAxis.setUpperBound(xMid + newXRange / 2);

            yAxis.setLowerBound(yMid - newYRange / 2);
            yAxis.setUpperBound(yMid + newYRange / 2);
            
            evt.consume();
        });
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
            updateComboBoxOptions();
        } else {
            showAlert("Advertencia", "Por favor selecciona un punto para eliminar");
        }
    }

    @FXML
    private void onClearData() {
        dataPoints.clear();
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

        updateComboBoxOptions();
    }

    @FXML
    private void onCalculate() {
        if (dataPoints.size() < 2) {
            showAlert("Error", "Se necesitan al menos 2 puntos para interpolar");
            return;
        }

        try {
            Integer selectedCount = pointsCountCombo.getValue();
            int n = (selectedCount != null && selectedCount <= dataPoints.size()) ? selectedCount : dataPoints.size();

            double[] xValues = new double[n];
            double[] yValues = new double[n];

            for (int i = 0; i < n; i++) {
                xValues[i] = dataPoints.get(i).getX();
                yValues[i] = dataPoints.get(i).getY();
            }

            solver = new NewtonDividedDifferences(xValues, yValues);

            populateDifferencesTable(xValues, solver.getDividedDifferencesTable());

            polynomialArea.setText(solver.getPolynomialString());
            polynomialArea.setStyle("-fx-font-size: 14; -fx-control-inner-background: #fff8f8;");

            renderProcessWithLatex(solver.getProcessSteps(), xValues);

            plotGraph(xValues, yValues);

            showAlert("Éxito", "Diferencias divididas calculadas correctamente");
        } catch (Exception e) {
            showAlert("Error", "Error al calcular: " + e.getMessage());
        }
    }

    private void plotGraph(double[] xValues, double[] yValues) {
        if (solver == null || interpolationChart == null) return;
        
        interpolationChart.getData().clear();
        
        XYChart.Series<Number, Number> pointsSeries = new XYChart.Series<>();
        pointsSeries.setName("Puntos dados");
        
        double minX = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        
        for (int i = 0; i < xValues.length; i++) {
            pointsSeries.getData().add(new XYChart.Data<>(xValues[i], yValues[i]));
            if (xValues[i] < minX) minX = xValues[i];
            if (xValues[i] > maxX) maxX = xValues[i];
        }
        
        double paddingX = Math.max(1.0, (maxX - minX) * 0.2);
        xAxis.setLowerBound(minX - paddingX);
        xAxis.setUpperBound(maxX + paddingX);

        XYChart.Series<Number, Number> polySeries = new XYChart.Series<>();
        polySeries.setName("Polinomio Interpolante");
        
        int steps = 100;
        double startX = xAxis.getLowerBound();
        double endX = xAxis.getUpperBound();
        double stepSize = (endX - startX) / steps;
        
        double minY = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        
        for (int i = 0; i <= steps; i++) {
            double currentX = startX + (i * stepSize);
            double currentY = solver.evaluate(currentX);
            polySeries.getData().add(new XYChart.Data<>(currentX, currentY));
            
            if (currentY < minY) minY = currentY;
            if (currentY > maxY) maxY = currentY;
        }
        
        double yPadding = Math.max(1.0, (maxY - minY) * 0.2);
        
        if (!Double.isInfinite(minY) && !Double.isInfinite(maxY)) {
            yAxis.setLowerBound(minY - yPadding);
            yAxis.setUpperBound(maxY + yPadding);
        } else {
            yAxis.setLowerBound(-100);
            yAxis.setUpperBound(100);
        }

        interpolationChart.getData().addAll(polySeries, pointsSeries);
    }

    /**
     * Llena la tabla real con los datos de diferencias divididas
     */
    private void populateDifferencesTable(double[] xValues, double[][] differences) {
        differenceRows.clear();
        int n = xValues.length;

        for (int i = 0; i < n; i++) {
            DifferenceRow row = new DifferenceRow(xValues[i], n);

            for (int j = 0; j < n; j++) {
                int diffRow = i - j;

                if (diffRow >= 0 && diffRow < n && j < n) {
                    if (differences[diffRow][j] != Double.NaN) {
                        row.setDifference(j, differences[diffRow][j]);
                    }
                }
            }

            differenceRows.add(row);
        }

        differencesTable.getColumns().clear();

        TableColumn<DifferenceRow, Double> xCol = new TableColumn<>("x");
        xCol.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        xCol.setPrefWidth(80);
        differencesTable.getColumns().add(xCol);

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

        differencesTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Renderiza el proceso con LaTeX
     */
    private void renderProcessWithLatex(java.util.List<String> processSteps, double[] xValues) {
        processContainer.getChildren().clear();

        for (String step : processSteps) {
            if (step.trim().isEmpty()) {
                processContainer.getChildren().add(new Separator());
            } else if (step.contains("===")) {
                Label title = new Label(step.replace("===", "").trim());
                title.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #2c3e50; -fx-padding: 10 0 5 0;");
                title.setWrapText(true);
                processContainer.getChildren().add(title);
            } else if (step.contains("---")) {
                Label subtitle = new Label(step.replace("---", "").trim());
                subtitle.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #34495e; -fx-padding: 8 0 4 0;");
                subtitle.setWrapText(true);
                processContainer.getChildren().add(subtitle);
            } else if (step.contains("f[") || step.contains("=") || step.contains("/")) {
                String latex = convertStepToLatex(step);
                try {
                    ImageView latexImage = LaTeXRenderer.renderLatex(latex);
                    latexImage.setStyle("-fx-padding: 5;");
                    processContainer.getChildren().add(latexImage);
                } catch (Exception e) {
                    Label label = new Label(step);
                    label.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 11;");
                    label.setWrapText(true);
                    processContainer.getChildren().add(label);
                }
            } else {
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

        latex = latex.replaceAll("(\\d+\\.?\\d*) / (\\d+\\.?\\d*)", "\\\\frac{$1}{$2}");

        latex = latex.replaceAll("(\\d+) / (\\d+)", "\\\\frac{$1}{$2}");

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
            
            highlightPointInChart(x, result);
        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor ingresa un número válido para x");
        }
    }

    private void highlightPointInChart(double x, double y) {
        if(interpolationChart == null || interpolationChart.getData().isEmpty()) return;
        
        ObservableList<XYChart.Series<Number, Number>> data = interpolationChart.getData();
        
        XYChart.Series<Number, Number> highlightSeries = null;
        for(XYChart.Series<Number, Number> s : data) {
            if("Punto Evaluado".equals(s.getName())) {
                highlightSeries = s;
                break;
            }
        }
        
        if(highlightSeries == null) {
            highlightSeries = new XYChart.Series<>();
            highlightSeries.setName("Punto Evaluado");
            data.add(highlightSeries);
        }
        
        highlightSeries.getData().clear();
        XYChart.Data<Number, Number> newPoint = new XYChart.Data<>(x, y);
        highlightSeries.getData().add(newPoint);
        
        if (x < xAxis.getLowerBound() || x > xAxis.getUpperBound() ||
            y < yAxis.getLowerBound() || y > yAxis.getUpperBound()) {

            double paddingX = (xAxis.getUpperBound() - xAxis.getLowerBound()) * 0.1;
            double paddingY = (yAxis.getUpperBound() - yAxis.getLowerBound()) * 0.1;

            xAxis.setLowerBound(x - paddingX);
            xAxis.setUpperBound(x + paddingX);

            yAxis.setLowerBound(y - paddingY);
            yAxis.setUpperBound(y + paddingY);
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
        if (interpolationChart != null) {
            interpolationChart.getData().clear();
        }
        solver = null;
    }

    /**
     * Actualiza dinámicamente las opciones del ComboBox basado en la cantidad de puntos
     */
    private void updateComboBoxOptions() {
        int currentPointCount = dataPoints.size();

        ObservableList<Integer> validCounts = FXCollections.observableArrayList();
        for (int i = 2; i <= currentPointCount; i++) {
            validCounts.add(i);
        }

        if (validCounts.isEmpty()) {
            validCounts = FXCollections.observableArrayList(2, 3, 4, 5, 6, 7, 8);
        }

        pointsCountCombo.setItems(validCounts);

        Integer currentValue = pointsCountCombo.getValue();
        if (currentValue == null || currentValue > currentPointCount) {
            pointsCountCombo.setValue(validCounts.get(0));
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
