package org.example.commands;

import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды группировки объектов коллекции по первой цифре ID
 */
public class GroupCountingByIdCommand extends Command {
    /**
     * @param realization объект realization
     */
    public GroupCountingByIdCommand(Realization realization) {
        super("group_counting_by_id", "", "Группировка всех элементов коллекции по значению поля id и вывод количества элементов в каждой группе.", 0, new String[]{}, new String[]{}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().groupCountingById(request);
        return response;
    }
}
