package org.example.commands;

import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды вывода всех элементов коллекции
 */
public class ShowCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ShowCommand(Realization realization) {
        super("show", "","Вывод всех элементов коллекции.", 0, new String[]{}, new String[]{}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().show(request);
        return response;
    }
}
