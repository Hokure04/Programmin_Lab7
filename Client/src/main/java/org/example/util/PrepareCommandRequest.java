package org.example.util;

import org.example.common.data.FuelType;
import org.example.common.data.User;
import org.example.common.data.Vehicle;
import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.StartRequest;
import org.example.managers.QueriesHandler;

import java.util.NoSuchElementException;

public class PrepareCommandRequest {
    private StartRequest startRequest;
    private QueriesHandler queriesHandler;
    private VehicleBuilder builder;

    public PrepareCommandRequest(QueriesHandler queriesHandler, StartRequest startRequest) {
        this.startRequest = startRequest;
        this.queriesHandler = queriesHandler;
        this.builder = new VehicleBuilder(queriesHandler);
    }

    public CommandRequest startPreparing(String command, String args, User user) throws TroubleObjectCreationException {
        Vehicle object = null;
        FuelType fuelType = null;
        for (String type : this.startRequest.getCommands().get(command).getTypeCompositArgs().values()) {
            if (type.equals("OBJECT")) {
                try {
                    object = this.builder.build(user.getLogin());
                } catch (NoSuchElementException e) {
                    throw e;
                }
            }
            if (type.equals("FUELTYPE")) {
                String var = "Возможные значения:";
                for (FuelType i : FuelType.values()) {
                    var += " " + i.name();
                }
                //System.out.println(var);
                String line = this.queriesHandler.query("Введите тип топлива. Значение должно быть одной из заранее определенных констант.\n"+var);
                try {
                    fuelType = FuelType.valueOf(line.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Ошибка: введенной константы типа топлива не существует");
                }
            }
        }
        CommandRequest request = new CommandRequest(command, args.split(" +", 2), object, fuelType, TypesRequest.COMMAND, user);
        return request;
    }
}
