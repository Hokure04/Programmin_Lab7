package org.example.common.requests;

import org.example.common.data.FuelType;
import org.example.common.data.User;
import org.example.common.data.Vehicle;

import java.io.Serializable;

public class CommandRequest extends BaseRequest implements Serializable {
    private String[] args;
    private Vehicle object;
    private FuelType fuelType;
    private User user;

    public CommandRequest(String command, String[] args, Vehicle object, FuelType fuelType, TypesRequest typeRequest, User user) {
        this.command = command;
        this.args = args;
        this.object = object;
        this.fuelType = fuelType;
        this.user = user;
        this.typeRequest = typeRequest;
    }

    public String[] getArgs() {
        return this.args;
    }

    public String getCommand() {
        return this.command;
    }

    public FuelType getFuelType() {
        return fuelType;
    }
    public Vehicle getObject() {
        return object;
    }

    public User getUser() {
        return user;
    }
}
