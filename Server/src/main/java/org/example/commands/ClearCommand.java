package org.example.commands;

import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды очистки коллекции
 */
public class ClearCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ClearCommand(Realization realization) {
        super("clear", "","Очистка коллекции.", 0, new String[]{}, new String[]{}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }
    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().clear(request);
        return response;
    }
}
