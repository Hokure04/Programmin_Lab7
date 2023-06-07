package org.example.util;

import org.example.common.data.Coordinates;
import org.example.common.exceptions.TroubleCoordinateCreationException;
import org.example.managers.QueriesHandler;

import java.util.NoSuchElementException;

public class CoordinatesBuilder {
    private QueriesHandler queriesHandler;
    private Double x = null;
    private Double y = null;

    /**
     *
     * @param queriesHandler объект queriesHandler
     */
    public CoordinatesBuilder(QueriesHandler queriesHandler) {
        this.queriesHandler = queriesHandler;
    }
    /**
     * Запуск создания
     */
    public Coordinates build() throws TroubleCoordinateCreationException {
        try {
            if (x == null) {
                this.x = writeX();
            }
            if (y == null) {
                this.y = writeY();
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new TroubleCoordinateCreationException(e.getLocalizedMessage());
        }
        return new Coordinates(x, y);
    }
    /**
     * Запрос координаты X
     */
    public double writeX() {
        try {
            Double x;
            try {
                x = Double.parseDouble(this.queriesHandler.query("Введите координату X. Она должна быть вещественным числом."));
                if (x.isInfinite()) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Ошибка: введенное число не является вещественным числом, либо превышена максимальная величина целого числа.");
            }
            return x;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    /**
     * Запрос координаты Y
     */
    public Double writeY() {
        try {
            Double y;
            try {
                y = Double.parseDouble(this.queriesHandler.query("Введите координату Y. Она должна быть вещественным числом."));
                if (y.isInfinite()) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Ошибка: введенное число не является вещественным числом, либо превышена максимальная величина целого числа.");
            }
            return y;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    /**
     * Зануление полей после создания
     */
    public void endBuild() {
        this.x = null;
        this.y = null;
    }
}