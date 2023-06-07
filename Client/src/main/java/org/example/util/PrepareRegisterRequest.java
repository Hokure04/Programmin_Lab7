package org.example.util;

import org.example.common.data.FuelType;
import org.example.common.data.User;
import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.RegisterRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.StartRequest;
import org.example.exceptions.UnauthorisedUserException;
import org.example.managers.QueriesHandler;

import java.util.NoSuchElementException;

public class PrepareRegisterRequest {
    private StartRequest startRequest;
    private QueriesHandler queriesHandler;
    private static final int MAX_STRING_LENGTH_LOGIN = 50;
    private static final int MAX_STRING_LENGTH_PASSWORD = 50;

    public PrepareRegisterRequest(QueriesHandler queriesHandler, StartRequest startRequest) {
        this.startRequest = startRequest;
        this.queriesHandler = queriesHandler;
    }

    String login;
    String password;
    public RegisterRequest startPreparing(String command, String args) {
        for (String type : this.startRequest.getCommands().get(command).getTypeCompositArgs().values()) {
            if (type.equals("LOGIN")) {
                login = this.queriesHandler.query("Введите логин");
                if (login.equals("")) {
                    this.queriesHandler.write("Логин не может быть пустой строкой");
                } else if (login.length() > MAX_STRING_LENGTH_LOGIN) {
                    this.queriesHandler.write("Логин должен быть короче 50 символов");
                }
            }
            if (type.equals("PASSWORD")) {
                password = this.queriesHandler.query("Введите пароль");
                if (password.equals("")) {
                    this.queriesHandler.write("Пароль не может быть пустой строкой");
                }else if (password.length() > MAX_STRING_LENGTH_PASSWORD) {
                    this.queriesHandler.write("Пароль должен быть короче 50 символов");
                }
            }
        }
        RegisterRequest request = new RegisterRequest(command, args.split(" +", 2), TypesRequest.REGISTER, new User(login, password));
        return request;
    }
}
