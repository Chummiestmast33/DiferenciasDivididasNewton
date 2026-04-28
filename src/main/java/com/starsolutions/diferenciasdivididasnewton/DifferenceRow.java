package com.starsolutions.diferenciasdivididasnewton;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Clase para representar una fila en la tabla de diferencias divididas
 */
public class DifferenceRow {
    private DoubleProperty x;
    private DoubleProperty[] differences;

    public DifferenceRow(double xVal, int numColumns) {
        this.x = new SimpleDoubleProperty(xVal);
        this.differences = new SimpleDoubleProperty[numColumns];
        for (int i = 0; i < numColumns; i++) {
            this.differences[i] = new SimpleDoubleProperty(Double.NaN);
        }
    }

    public double getX() {
        return x.get();
    }

    public void setX(double value) {
        x.set(value);
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getDifference(int index) {
        if (index < 0 || index >= differences.length) {
            return Double.NaN;
        }
        return differences[index].get();
    }

    public void setDifference(int index, double value) {
        if (index >= 0 && index < differences.length) {
            differences[index].set(value);
        }
    }

    public DoubleProperty differenceProperty(int index) {
        if (index < 0 || index >= differences.length) {
            return null;
        }
        return differences[index];
    }

    public int getColumnCount() {
        return differences.length;
    }
}
