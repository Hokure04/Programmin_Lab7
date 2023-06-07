package org.example.commands;

import org.example.common.exceptions.NoAccessToTheFileException;
import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

import javax.swing.*;
import java.io.FileNotFoundException;
/**
 * Класс базовой команды
 */
abstract public class Command {
    private String description;
    private String name;
    private String args;
    private int countArgs;
    private String[] typeArgs;
    private String[] typeCompositArgs;
    private Realization realization;
    private TypesRequest typeRequest;
    private TypeAccess typeAccess;

    /**
     * @param name название команды
     * @param description описание команды
     * @param countArgs число аргументов команды
     * @param realization объект realization
     */
    public Command(String name, String args, String description, int countArgs, String[] typeArgs, String[] typeCompositArgs, TypesRequest typeRequest, TypeAccess typeAccess, Realization realization) {
        this.name = name;
        this.args = args;
        this.description = description;
        this.countArgs = countArgs;
        this.typeArgs = typeArgs;
        this.typeCompositArgs = typeCompositArgs;
        this.realization = realization;
        this.typeRequest = typeRequest;
        this.typeAccess = typeAccess;
    }
    /**
     * Получить описание команды
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Получить название команды
     */
    public String getName() {
        return this.name;
    }
    /**
     * Получить число аргументов
     */
    public int getCountArgs() {
        return this.countArgs;
    }

    public String[] getTypeArgs() {
        return typeArgs;
    }

    public String[] getTypeCompositArgs() {
        return typeCompositArgs;
    }

    /**
     * Получить объект класса реализаций
     */
    public Realization getRealization() {
        return realization;
    }

//    /**
//     * Выполнить команду
//     * @param line строка с аргументами
//     */
//    public void execute(String line) throws UnknownCommandException, TroubleObjectCreationException, FileNotFoundException, NoAccessToTheFileException {
//    }
    public BaseResponse execute(BaseRequest request) {
        return null;
    }

    public String getArgs() {
        return args;
    }

    public TypesRequest getTypeRequest() {
        return typeRequest;
    }

    public TypeAccess getTypeAccess() {
        return typeAccess;
    }
}