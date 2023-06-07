package org.example.managers.database;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.common.data.Coordinates;
import org.example.common.data.FuelType;
import org.example.common.data.Vehicle;
import org.example.common.data.VehicleType;
import org.example.managers.CollectionHandler;

import java.sql.*;
import java.util.LinkedHashMap;

public class DatabaseCollectionHandler {
    private static final Logger log = LogManager.getLogger("logfile.txt");
    private final CollectionHandler<Vehicle> collectionHandler;
    private Connection database;

    /**
     *
     * @param collectionHandler объект collectionHandler
     */
    public DatabaseCollectionHandler(Connection database, CollectionHandler<Vehicle> collectionHandler) {
        this.collectionHandler = collectionHandler;
        this.database = database;
        createDBCollection();
        deserial();
        //this.xStream.alias("vehicle", Vehicle.class);
    }

    public void insertCollectionByKey(Integer key, Vehicle element) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "INSERT INTO collection(key, name, x, y, creationdate, enginepower, type, fueltype, creator)" +
                            "VALUES (?, ?, ?, ?, ?, ?, CAST(? AS type), CAST(? AS fueltype), ?)"
            );
            ps.setInt(1, key);
            ps.setString(2, element.getName());
            ps.setDouble(3, element.getCoordinates().getX());
            ps.setDouble(4, element.getCoordinates().getY());
            ps.setDate(5, Date.valueOf(element.getCreationDate()));
            ps.setInt(6, Math.toIntExact(element.getEnginePower()));
            ps.setString(7, element.getType().toString());
            ps.setString(8, element.getFuelType().toString());
            ps.setString(9, element.getCreator());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Произошел пиздец у insertCollectionByKey");
            throw new RuntimeException(e);
        }
    }

    public void replaceCollectionById(Integer id, Vehicle element, String user) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "UPDATE collection " +
                            "SET name=?," +
                            "x=?," +
                            "y=?," +
                            "creationdate=?," +
                            "enginepower=?," +
                            "type=CAST(? AS type)," +
                            "fueltype=CAST(? AS fueltype)" +
                            "WHERE id=? AND creator=?"
            );
            ps.setString(1, element.getName());
            ps.setDouble(2, element.getCoordinates().getX());
            ps.setDouble(3, element.getCoordinates().getY());
            ps.setDate(4, Date.valueOf(element.getCreationDate()));
            ps.setInt(5, Math.toIntExact(element.getEnginePower()));
            ps.setString(6, element.getType().toString());
            ps.setString(7, element.getFuelType().toString());
            ps.setInt(8, id);
            ps.setString(9, user);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Произошел пиздец у replaceCollectionById");
            throw new RuntimeException(e);
        }
    }

    public void removeCollectionByKey(Integer key, String user) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "DELETE FROM collection WHERE key=? AND creator=?;"
            );
            ps.setInt(1, key);
            ps.setString(2, user);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошел пиздец у removeCollectionByKey");
            throw new RuntimeException(e);
        }
    }

    public void removeCollectionGreaterThan(Vehicle element, String user) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "DELETE FROM collection WHERE enginepower>? AND creator=?;"
            );
            ps.setInt(1, Math.toIntExact(element.getEnginePower()));
            ps.setString(2, user);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошел пиздец у removeCollectionGreaterThan");
            throw new RuntimeException(e);
        }
    }

    public void replaceCollectionByKey(Integer key, Vehicle element, String user) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "UPDATE collection " +
                            "SET name=?," +
                            "x=?," +
                            "y=?," +
                            "creationdate=?," +
                            "enginepower=?," +
                            "type=CAST(? AS type)," +
                            "fueltype=CAST(? AS fueltype)" +
                            "WHERE key=? AND enginepower>? AND creator=?"
            );
            ps.setString(1, element.getName());
            ps.setDouble(2, element.getCoordinates().getX());
            ps.setDouble(3, element.getCoordinates().getY());
            ps.setDate(4, Date.valueOf(element.getCreationDate()));
            ps.setInt(5, Math.toIntExact(element.getEnginePower()));
            ps.setString(6, element.getType().toString());
            ps.setString(7, element.getFuelType().toString());
            ps.setInt(8, key);
            ps.setInt(9, Math.toIntExact(element.getEnginePower()));
            ps.setString(10, user);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Произошел пиздец у replaceCollectionByKey");
            throw new RuntimeException(e);
        }
    }

    public void removeCollectionByLowerKey(Integer key, String user) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "DELETE FROM collection WHERE key<? AND creator=?;"
            );
            ps.setInt(1, key);
            ps.setString(2, user);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошел пиздец у removeCollectionByLowerKey");
            throw new RuntimeException(e);
        }
    }
    public void clearCollection(String user) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "DELETE FROM collection WHERE creator=?;"
            );
            ps.setString(1, user);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошел пиздец у clearCollection");
            throw new RuntimeException(e);
        }
    }

    public int getIdFromCollection(Integer key) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "SELECT id FROM collection WHERE key=?;"
            );
            ps.setInt(1, key);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            return id;
        } catch (SQLException e) {
            System.out.println("Произошел пиздец у getIdFromCollection");
            throw new RuntimeException(e);
        }
    }

    public void deserial() {
        try {
            ResultSet rs = this.database.createStatement().executeQuery(
                    "SELECT * FROM collection"
            );
            this.collectionHandler.setNewCollection();
            while (rs.next()) {
                Vehicle element = new Vehicle(
                        rs.getInt(2),
                        rs.getString(3),
                        new Coordinates(rs.getLong(4), rs.getDouble(5)),
                        rs.getString(6),
                        rs.getLong(7),
                        VehicleType.valueOf(rs.getString(8)),
                        FuelType.valueOf(rs.getString(9)),
                        rs.getString(10));
                this.collectionHandler.addElement(rs.getInt(1), element);
            }
        } catch (SQLException e) {
            System.out.println("SUS при десериализации коллекции");
        }
    }

    public void createDBCollection() {
        //this.database.createStatement().executeUpdate("CREATE TYPE t AS ENUM ('CAR', 'PLANE', 'BOAT', 'SPACESHIP');");
        //this.database.createStatement().executeUpdate("CREATE TYPE ft AS ENUM ('KEROSENE', 'ELECTRICITY', 'PLASMA', 'NUCLEAR', 'ANTIMATTER');");
        try {
            this.database.createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS collection(" +
                            "key integer NOT NULL," +
                            "id serial PRIMARY KEY," +
                            "name varchar(50) NOT NULL," +
                            "x bigint NOT NULL," +
                            "y double precision NOT NULL," +
                            "creationDate date NOT NULL," +
                            "enginePower integer NOT NULL," +
                            "type type NOT NULL," +
                            "fuelType fueltype NOT NULL," +
                            "creator varchar(50) NOT NULL);"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public void setDatabase(Connection database) {
        this.database = database;
    }
}
