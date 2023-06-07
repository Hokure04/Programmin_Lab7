package org.example.managers.database;

import org.example.common.exceptions.NoAccessToTheFileException;
import org.example.managers.CollectionHandler;
import org.example.managers.UserHandler;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseManager {
    private CollectionHandler collectionHandler;
    private UserHandler userHandler;
    private DatabaseCollectionHandler databaseCollectionHandler;
    private DatabaseUserHandler databaseUserHandler;
    private Connection database;

    public DatabaseManager(CollectionHandler collectionHandler, UserHandler userHandler) {
        this.collectionHandler = collectionHandler;
        this.userHandler = userHandler;
    }

    public DatabaseCollectionHandler getDatabaseCollectionHandler() {
        return databaseCollectionHandler;
    }

    public DatabaseUserHandler getDatabaseUserHandler() {
        return databaseUserHandler;
    }

    public void connectDB() throws FileNotFoundException, NoAccessToTheFileException {
        try {
            File pgpass = new File("src/main/resources/.pgpass");
            if (!pgpass.isFile()) throw new FileNotFoundException("Файла .pgpass не существует");
            if (!pgpass.canWrite())
                throw new NoAccessToTheFileException("Программа не имеет права на чтение файла .pgpass");
            BufferedReader pgpassStream = new BufferedReader(new FileReader(pgpass));
            String line;
            try {
                line = pgpassStream.readLine();
            } catch (IOException e) {
                throw new FileNotFoundException("Файл .pgpass пустой");
            }
            String[] els = line.split(":");

            String url = "jdbc:postgresql://localhost:5432/studs";
            //String user = "s368206";
            //String password = "QJ4ldzTvj3zmMwhl";
            String user = els[3];
            String password = els[4];

            try {
                this.database = DriverManager.getConnection(url, user, password);

                this.databaseCollectionHandler = new DatabaseCollectionHandler(this.database, collectionHandler);
                this.databaseUserHandler = new DatabaseUserHandler(this.database, userHandler);

                //this.databaseCollectionHandler.createDBCollection();
                //this.databaseUserHandler.createDBUser();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Ошибка при подключении к БД");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
