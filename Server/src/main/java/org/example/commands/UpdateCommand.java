package org.example.commands;

import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды замена объекта по id
 */
public class UpdateCommand extends Command {
    /**
     * @param realization объект realization
     */
    public UpdateCommand(Realization realization) {
        super("update", "id {element}", "Замена объекта с указанным id. id является положительным целым числом.", 1, new String[]{"ID"}, new String[]{"OBJECT"}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().update(request);
        return response;
    }
}
