package org.example.commands;

import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды удаления всех элементов в коллекции, чей ключ меньше заданного
 */
public class RemoveLowerKeyCommand extends Command {
    /**
     * @param realization объект realization
     */
    public RemoveLowerKeyCommand(Realization realization) {
        super("remove_lower_key", "null", "Удаление из коллекции всех элементов, чей ключ меньше заданного. Ключ является положительным целым числом.", 1, new String[]{"KEY"}, new String[]{}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().removeLowerKey(request);
        return response;
    }
}
