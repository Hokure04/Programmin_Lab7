package org.example.commands;

import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды вывода справки по командам
 */
public class HelpCommand extends Command {
    /**
     * @param realization объект realization
     */
    public HelpCommand(Realization realization) {
        super("help", "", "Вывод справки по командам.", 0, new String[]{}, new String[]{}, TypesRequest.COMMAND, TypeAccess.NOTAUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().help(request);
        return response;
    }
}
