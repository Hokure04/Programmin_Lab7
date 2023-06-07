package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.example.common.data.Vehicle;
import org.example.commands.*;
import org.example.managers.*;
import org.example.managers.database.DatabaseManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;

/**
 * Стартовый класс
 */
public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    /**
     * Стартовый метод
     */
    public static void main(String[] args) {
        log.info("Начало работы сервера");
        CollectionHandler<Vehicle> collectionHandler = new CollectionHandler<>();
        UserHandler userHandler = new UserHandler();
        //collectionHandler.setPathToFile("src/main/java/org/example/files/db.xml");
        CommandHandler commandHandler = new CommandHandler();
        DatabaseManager databaseManager = new DatabaseManager(collectionHandler, userHandler);

        //Saver saver = new Saver(fileHandler);

        Realization realization = new Realization(collectionHandler, userHandler, commandHandler, databaseManager);

        commandHandler.addCommand("help", new HelpCommand(realization));
        commandHandler.addCommand("info", new InfoCommand(realization));
        commandHandler.addCommand("show", new ShowCommand(realization));
        commandHandler.addCommand("insert", new InsertCommand(realization));
        commandHandler.addCommand("update", new UpdateCommand(realization));
        commandHandler.addCommand("remove_key", new RemoveKeyCommand(realization));
        commandHandler.addCommand("clear", new ClearCommand(realization));
        //commandHandler.addCommand("save", new SaveCommand(realization));
        commandHandler.addCommand("execute_script", new ExecuteScriptCommand(realization));
        commandHandler.addCommand("exit", new ExitCommand(realization));
        commandHandler.addCommand("remove_greater", new RemoveGreaterCommand(realization));
        commandHandler.addCommand("replace_if_lower", new ReplaceIfLowerCommand(realization));
        commandHandler.addCommand("remove_lower_key", new RemoveLowerKeyCommand(realization));
        commandHandler.addCommand("group_counting_by_id", new GroupCountingByIdCommand(realization));
        commandHandler.addCommand("filter_greater_than_fuel_type", new FilterGreaterThanFuelTypeCommand(realization));
        commandHandler.addCommand("print_descending", new PrintDescendingCommand(realization));
        commandHandler.addCommand("register", new RegisterCommand(realization));
        commandHandler.addCommand("enter", new EnterCommand(realization));

        ConnectManager connectManager = new ConnectManager(args, databaseManager, collectionHandler, userHandler, commandHandler);
        connectManager.activeCatching();
    }
}