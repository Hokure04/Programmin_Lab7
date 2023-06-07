package org.example.common.data;

import org.example.common.exceptions.TroubleCoordinateCreationException;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Класс координат
 */
public class Coordinates implements Serializable {
    private double x;
    private Double y; //Поле не может быть null

    /**
     * @param x координата X
     * @param y координата Y
     */
    public Coordinates(double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("x - %s; y - %s", this.x, this.y.toString());
    }

    public double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    /**
     * Класс-билдер координат
     */
}