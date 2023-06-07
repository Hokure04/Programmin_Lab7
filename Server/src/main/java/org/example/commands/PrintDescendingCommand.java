package org.example.commands;

import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды вывода элементов коллекции в порядке убывания
 */
public class PrintDescendingCommand extends Command {
    /**
     * @param realization объект realization
     */
    public PrintDescendingCommand(Realization realization) {
        super("print_descending", "","Вывод элементов коллекции в порядке убывания значения enginePower.", 0, new String[]{}, new String[]{}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().printDescending(request);
        return response;
    }
}
