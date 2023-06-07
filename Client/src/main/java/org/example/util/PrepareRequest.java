package org.example.util;

import org.example.common.data.FuelType;
import org.example.common.data.User;
import org.example.common.data.Vehicle;
import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.StartRequest;
import org.example.exceptions.UnauthorisedUserException;
import org.example.managers.QueriesHandler;
import org.example.managers.Validator;

import java.util.NoSuchElementException;

public class PrepareRequest {
    private PrepareCommandRequest prepareCommandRequest;
    private PrepareRegisterRequest prepareRegisterRequest;
    private StartRequest startRequest;
    private QueriesHandler queriesHandler;
    private VehicleBuilder builder;
    public PrepareRequest(QueriesHandler queriesHandler, StartRequest startRequest) {
        this.startRequest = startRequest;
        this.queriesHandler = queriesHandler;
        this.builder = new VehicleBuilder(queriesHandler);
        this.prepareCommandRequest = new PrepareCommandRequest(queriesHandler, startRequest);
        this.prepareRegisterRequest = new PrepareRegisterRequest(queriesHandler, startRequest);
    }
    public BaseRequest startPreparing(String command, String args, User user) throws TroubleObjectCreationException, UnknownCommandException, UnauthorisedUserException {
        Validator.validateCommand(command, this.startRequest);
        Validator.validateCountArgs(args.split(" +", 2), this.startRequest.getCommands().get(command).getCountArgs());
        int count = 0;
        if (args != "") {
            for (String arg : args.split(" +", 2)) {
                String type = this.startRequest.getCommands().get(command).getTypeArgs().get(count);
                if (type.equals("ID")) {
                    Validator.validateId(arg);
                } else if (type.equals("KEY")) {
                    Validator.validateKey(arg);
                }
                count += 1;
            }
        }

        if (this.startRequest.getCommands().get(command).getTypeRequest().equals(TypesRequest.COMMAND)) {
            return this.prepareCommandRequest.startPreparing(command, args, user);
        } else if (this.startRequest.getCommands().get(command).getTypeRequest().equals(TypesRequest.REGISTER)) {
            return this.prepareRegisterRequest.startPreparing(command, args);
        }
        return null;
    }
}
