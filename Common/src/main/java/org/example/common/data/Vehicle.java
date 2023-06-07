package org.example.common.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * Класс транспорта
 */
@XStreamAlias("vehicle")
public class Vehicle implements Comparable<Vehicle>, Serializable {
    private static Integer maxId = 0;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long enginePower; //Поле может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле может быть null
    private FuelType fuelType; //Поле не может быть null
    private String creator;

    /**
     *
     * @param name название
     * @param coordinates объект координат
     * @param enginePower мощность
     * @param type тип транспорта
     * @param fuelType тип топлива
     */
    public Vehicle(String name, Coordinates coordinates, Long enginePower, VehicleType type, FuelType fuelType, String creator) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.type = type;
        this.fuelType = fuelType;
        //this.id = maxId+1;
        //maxId += 1;
        this.id = null;
        this.creator = creator;
    }

    public Vehicle(Integer id, String name, Coordinates coordinates, Long enginePower, VehicleType type, FuelType fuelType, String creator) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.type = type;
        this.fuelType = fuelType;
        this.id = id;
        this.creator = creator;
    }
    public Vehicle(Integer id, String name, Coordinates coordinates, String creationDate, Long enginePower, VehicleType type, FuelType fuelType, String creator) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.parse(creationDate);
        this.enginePower = enginePower;
        this.type = type;
        this.fuelType = fuelType;
        this.creator = creator;
    }
    public Vehicle(String name, Coordinates coordinates, String creationDate, Long enginePower, VehicleType type, FuelType fuelType, String creator) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.parse(creationDate);
        this.enginePower = enginePower;
        this.type = type;
        this.fuelType = fuelType;
        this.creator = creator;
    }

    public static void init(Integer id) {
        maxId = id;
    }
    /**
     * Получить ID
     */
    public Integer getId() {
        return this.id;
    }
    /**
     * Получить мощность
     */
    public Long getEnginePower() {
        return enginePower;
    }
    /**
     * Получить тип топлива
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Получить координаты
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Получить дату создания
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Получить тип транспортного средства
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Получить название
     */
    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Получить все поля объекта
     */
    public String[] getAll() {
        String[] a = new String[8];
        a[0] = this.getId().toString();
        a[1] = this.getName();
        a[2] = this.getCoordinates().toString();
        a[3] = this.getCreationDate().toString();
        a[4] = this.getEnginePower().toString();
        a[5] = this.getType().toString();
        a[6] = this.getFuelType().toString();
        a[7] = this.getCreator();
        return a;
    }

    @Override
    public String toString() {
        return String.format("id - %s; name - %s; coordinates - %s; creationDate - %s; " +
                "enginePower - %s; type - %s; fuelType - %s", this.id.toString(), this.name, this.coordinates.toString(),
                this.creationDate.toString(), this.enginePower.toString(), this.type.toString(), this.fuelType.toString());
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        return (int) (this.enginePower - vehicle.getEnginePower());
    }
    /**
     * Класс-билдер транспорта
     */

}