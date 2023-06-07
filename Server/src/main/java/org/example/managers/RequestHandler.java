package org.example.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.commands.RegisterCommand;
import org.example.common.data.User;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.RegisterRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.common.responses.ResponseResult;
import org.example.common.responses.TypesResponse;
import org.example.exceptions.MissingCommandException;
import org.example.managers.customers.ResponseSender;
import org.example.managers.database.DatabaseUserHandler;
import org.example.util.Encryptor;
import org.example.util.TypeAccess;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.Executor;

public class RequestHandler implements Runnable {
    private static final Logger log = LogManager.getLogger("logfile.txt");
    private CommandHandler commandHandler;
    private DatabaseUserHandler databaseUserHandler;
    private UserHandler userHandler;
    private BaseRequest baseRequest;
    private Executor executor;
    private SelectionKey selectionKey;

    public RequestHandler(CommandHandler commandHandler, DatabaseUserHandler databaseUserHandler, UserHandler userHandler, BaseRequest baseRequest, Executor executor, SelectionKey selectionKey) {
        this.commandHandler = commandHandler;
        this.databaseUserHandler = databaseUserHandler;
        this.userHandler = userHandler;
        this.baseRequest = baseRequest;
        this.executor = executor;
        this.selectionKey = selectionKey;
    }

    public void run() {
        handle(this.baseRequest);
    }

    public void handle(BaseRequest baseRequest) {
        try {
            if (baseRequest.getTypeRequest().equals(TypesRequest.COMMAND)) {
                CommandRequest request = (CommandRequest) baseRequest;

                if (this.commandHandler.getCommands().get(request.getCommand()).getTypeAccess().equals(TypeAccess.NOTAUTHORISED)) {
                    this.executor.execute(new ResponseSender(this.selectionKey, this.commandHandler.runCommand(baseRequest)));
                    //return this.commandHandler.runCommand(baseRequest);
                } else if (this.commandHandler.getCommands().get(request.getCommand()).getTypeAccess().equals(TypeAccess.AUTHORISED)) {
                    if (request.getUser() != null) {
                        if (this.userHandler.checkUserLoginExist(request.getUser().getLogin())) {
                            String[] els = this.databaseUserHandler.getPassword(request.getUser().getLogin());
                            if (Encryptor.getSHA384Password(request.getUser().getPassword(), els[1]).equals(els[0])) {
                                this.executor.execute(new ResponseSender(this.selectionKey, this.commandHandler.runCommand(baseRequest)));
                                //return this.commandHandler.runCommand(baseRequest);
                            } else {
                                this.executor.execute(new ResponseSender(this.selectionKey, new ResponseResult(0, "Пароль введен неверно", TypesResponse.RESULT, null)));
                                //return new ResponseResult(0, "Пароль введен неверно", TypesResponse.RESULT, null);
                            }
                        } else {
                            this.executor.execute(new ResponseSender(this.selectionKey, new ResponseResult(0, "Такого пользователя не существует", TypesResponse.RESULT, null)));
                            //return new ResponseResult(0, "Такого пользователя не существует", TypesResponse.RESULT, null);
                        }
                    } else {
                        this.executor.execute(new ResponseSender(this.selectionKey, new ResponseResult(0, "Для вызова команды необходимо авторизоваться", TypesResponse.RESULT, null)));
                        //return new ResponseResult(0, "Для вызова команды необходимо авторизоваться", TypesResponse.RESULT, null);
                    }
                }

            } else if (baseRequest.getTypeRequest().equals(TypesRequest.REGISTER)) {
                this.executor.execute(new ResponseSender(this.selectionKey, this.commandHandler.runCommand(baseRequest)));
                //return this.commandHandler.runCommand(baseRequest);
            }
            //return null;
        } catch (MissingCommandException e) {
            log.error(e.getMessage());
        }
    }
}
