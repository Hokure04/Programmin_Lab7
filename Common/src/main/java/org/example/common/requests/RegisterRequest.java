package org.example.common.requests;

import org.example.common.data.User;

import java.io.Serializable;

public class RegisterRequest extends BaseRequest implements Serializable {
    private User user;
    private String[] args;

    public RegisterRequest(String command, String[] args, TypesRequest typeRequest, User user) {
        this.user = user;
        this.typeRequest = typeRequest;
        this.command = command;
        this.args = args;
    }

    public User getUser() {
        return user;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}
