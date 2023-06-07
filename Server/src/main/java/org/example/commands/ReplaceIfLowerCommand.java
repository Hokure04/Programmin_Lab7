package org.example.commands;

import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды замены элемента по ключу, если новый объект меньше старого
 */
public class ReplaceIfLowerCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ReplaceIfLowerCommand(Realization realization) {
        super("replace_if_lower", "null {element}", "Замена элемента на заданный по заданному ключу в случае, если значение enginePower заданного объекта меньше, чем у нынешнего. Ключ является положительным целым числом.", 1, new String[]{"KEY"}, new String[]{"OBJECT"}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().replaceIfLower(request);
        return response;
    }
}
