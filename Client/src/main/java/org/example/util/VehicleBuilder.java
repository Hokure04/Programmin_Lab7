package org.example.util;

import org.example.common.data.Coordinates;
import org.example.common.data.FuelType;
import org.example.common.data.Vehicle;
import org.example.common.data.VehicleType;
import org.example.managers.QueriesHandler;
import org.example.common.exceptions.EmptyValueException;
import org.example.common.exceptions.TroubleObjectCreationException;

import java.util.NoSuchElementException;

public class VehicleBuilder {
    private final QueriesHandler queriesHandler;
    private String name = null;
    private CoordinatesBuilder coordinatesBuilder = null;
    private Coordinates coordinates = null;
    private Long enginePower = null;
    private VehicleType type = null;
    private FuelType fuelType = null;

    private static final int MAX_STRING_LENGTH_NAME = 50;

    /**
     *
     * @param queriesHandler объект queriesHandler
     */
    public VehicleBuilder(QueriesHandler queriesHandler) {
        this.queriesHandler = queriesHandler;
    }

    public void createFields() throws TroubleObjectCreationException {
        try {
            if (this.name == null) {
                this.name = writeName();
            }
            if (this.coordinatesBuilder == null) {
                this.coordinatesBuilder = new CoordinatesBuilder(queriesHandler);
            }
            if (this.coordinates == null) {
                this.coordinates = this.coordinatesBuilder.build();
            }
            if (this.enginePower == null) {
                this.enginePower = writeEnginePower();
            }
            if (this.type == null) {
                this.type = writeVehicleType();
            }
            if (this.fuelType == null) {
                this.fuelType = writeFuelType();
            }
        } catch (NoSuchElementException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TroubleObjectCreationException(e.getLocalizedMessage());
        }
    }

    /**
     * Запуск создания
     */
    public Vehicle build(String user) throws TroubleObjectCreationException {
        try {
            createFields();
            Vehicle element = new Vehicle(this.name, this.coordinates, this.enginePower, this.type, this.fuelType, user);
            endBuild();
            return element;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    public Vehicle build(Integer id, String user) throws TroubleObjectCreationException {
        createFields();
        Vehicle element = new Vehicle(id, this.name, this.coordinates, this.enginePower, this.type, this.fuelType, user);
        endBuild();
        return element;
    }
    /**
     * Запрос названия
     */
    public String writeName() throws EmptyValueException, TroubleObjectCreationException {
        try {
            String name = this.queriesHandler.query("Введите имя объекта.");
            if (name.equals("")) throw new EmptyValueException("Ошибка: имя не может быть пустой строкой");
            if (name.length() > MAX_STRING_LENGTH_NAME) throw new TroubleObjectCreationException("Имя должно быть короче 50 символов");
            return name;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    /**
     * Запрос мощности
     */
    public Long writeEnginePower() {
        Long power;
        try {
            power = Long.parseLong(this.queriesHandler.query("Введите мощность двигателя. Значение должно быть целым и положительным."));
            if (power <= 0) {
                throw new TroubleObjectCreationException("Ошибка: значение мощности двигателя должно быть положительным");
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Ошибка: введенное значение не является целым числом, либо превышена максимальная величина целого числа.");
        } catch (TroubleObjectCreationException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        } catch (NoSuchElementException e) {
            throw e;
        }

        return power;
    }
    /**
     * Запрос типа транспорта
     */
    public VehicleType writeVehicleType() {
        try {
            String var = "Возможные значения:";
            for (VehicleType i : VehicleType.values()) {
                var += " " + i.name();
            }
            //System.out.println(var);
            String line = this.queriesHandler.query("Введите тип транспорта. Значение должно быть одной из заранее определенных констант.\n" + var);
            VehicleType type;
            try {
                type = VehicleType.valueOf(line.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ошибка: введенной константы типа транспорта не существует");
            }
            return type;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    /**
     * Запрос типа топлива
     */
    public FuelType writeFuelType() {
        try {
            String var = "Возможные значения:";
            for (FuelType i : FuelType.values()) {
                var += " " + i.name();
            }
            //System.out.println(var);
            String line = this.queriesHandler.query("Введите тип топлива. Значение должно быть одной из заранее определенных констант.\n" + var);
            FuelType type;
            try {
                type = FuelType.valueOf(line.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ошибка: введенной константы типа топлива не существует");
            }
            return type;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    /**
     * Зануление полей после создания
     */
    public void endBuild() {
        this.name = null;
        this.coordinatesBuilder = null;
        this.coordinates = null;
        this.enginePower = null;
        this.type = null;
        this.fuelType = null;
    }
}
