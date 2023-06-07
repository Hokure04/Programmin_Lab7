package org.example.commands;

import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
// * Класс команды удаления из коллекции всех элементов, больших заданного
 */
public class RemoveGreaterCommand extends Command {
    /**
     * @param realization объект realization
     */
    public RemoveGreaterCommand(Realization realization) {
        super("remove_greater", "{element}", "Удаление из коллекции всех элементов, значение enginePower которых больше, чем у заданного объекта", 0, new String[]{}, new String[]{"OBJECT"}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().removeGreater(request);
        return response;
    }
}
