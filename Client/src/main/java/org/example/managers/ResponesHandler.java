package org.example.managers;

import org.example.common.data.User;
import org.example.common.responses.*;

public class ResponesHandler {
    private QueriesHandler queriesHandler;
    private Parser parser;

    public ResponesHandler(QueriesHandler queriesHandler, Parser parser) {
        this.queriesHandler = queriesHandler;
        this.parser = parser;
    }

    public void handle(String command, BaseResponse baseResponse) {
        if (baseResponse.getTypeResponse().equals(TypesResponse.RESULT)) {
            ResponseResult response = (ResponseResult) baseResponse;
            if (response.getStatus() == 1) {
                if (response.getValue() != null) {
                    this.queriesHandler.write(response.getValue());
                }
                if (this.queriesHandler.getType() == QueriesHandlerType.FILE & (!command.equals("execute_script") | this.queriesHandler.getQueue().size() != 1)) {
                    this.queriesHandler.write(command + ": выполнено");
                }
            } else if (response.getStatus() == 0) {
                this.queriesHandler.write(response.getError());
            } else if (response.getStatus() == 3) {
                System.exit(1);
            }
        } else if (baseResponse.getTypeResponse().equals(TypesResponse.REQISTER)) {
            ResponseRegistration response = (ResponseRegistration) baseResponse;
            if (response.getStatus() == 1) {
                if (response.getValue() != null) {
                    this.queriesHandler.write(response.getValue());
                }
                this.parser.setUser(response.getUser());
            } else if (response.getStatus() == 0) {
                this.queriesHandler.write(response.getError());
            }
        }
    }
}
