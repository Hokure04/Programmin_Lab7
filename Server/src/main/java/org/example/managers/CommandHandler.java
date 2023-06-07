package org.example.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.commands.Command;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.responses.BaseResponse;
import org.example.common.responses.ResponseResult;
import org.example.exceptions.MissingCommandException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс для обработки команд
 */
public class CommandHandler {
    private static final Logger log = LogManager.getLogger("logfile.txt");
    private final Map<String, Command> commands;

    public CommandHandler() {
        this.commands = new LinkedHashMap<String, Command>();
    }
    /**
     * Добавить новую команду
     * @param name название команды
     * @param command объект команды
     */
    public void addCommand(String name, Command command) {
        this.commands.put(name, command);
    }
    /**
     * Вызов команды
     //* @param command название команды
     //* @param args аргументы команды
     */
    public BaseResponse runCommand(BaseRequest request) throws MissingCommandException {
        if (!this.commands.containsKey(request.getCommand())) {
            log.error("Полученная команда не существует");
            throw new MissingCommandException("Полученная команда не существует");
            //return new ResponseResult(0, "Ошибка: несуществующая команда", null);
        }
        BaseResponse response = this.commands.get(request.getCommand()).execute(request);
        //System.out.println(this.builders.get(command).);
        return response;
    }

    public Map<String, Command> getCommands() {
        return this.commands;
    }
}