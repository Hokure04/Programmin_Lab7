package org.example.commands;

import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды удаления элемента по ключу
 */
public class RemoveKeyCommand extends Command {
    /**
     * @param realization объект realization
     */
    public RemoveKeyCommand(Realization realization) {
        super("remove_key", "null", "Удаление элемента по указанному ключу. Ключ является положительным целым числом.", 1, new String[]{"KEY"}, new String[]{}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().removeKey(request);
        return response;
    }
}
