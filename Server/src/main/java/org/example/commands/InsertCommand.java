package org.example.commands;

import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды добавления нового элемента по заданному ключу
 */
public class InsertCommand extends Command {
    /**
     * @param realization объект realization
     */
    public InsertCommand(Realization realization) {
        super("insert", "null {element}", "Добавление нового элемента с заданным ключом. Ключ является положительным целым числом.", 1, new String[]{"KEY"}, new String[]{"OBJECT"}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().insert(request);
        return response;
    }
}
