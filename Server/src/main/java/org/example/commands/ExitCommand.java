package org.example.commands;

import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды выхода из программы
 */
public class ExitCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ExitCommand(Realization realization) {
        super("exit", "", "Завершение программы без сохранения коллекции в файл", 0, new String[]{}, new String[]{}, TypesRequest.COMMAND, TypeAccess.NOTAUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().exit(request);
        return response;
    }
}
