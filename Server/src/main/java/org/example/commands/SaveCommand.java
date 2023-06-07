package org.example.commands;

import org.example.common.requests.TypesRequest;
import org.example.util.TypeAccess;

/**
 * Класс команды сохранения коллекции
 */
public class SaveCommand extends Command {
    /**
     * @param realization объект realization
     */
    public SaveCommand(Realization realization) {
        super("save", "","Сохранение коллекции в файл", 0, new String[]{}, new String[]{}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    /*@Override
    public Response execute(CommandRequest request) throws UnknownCommandException, NoAccessToTheFileException, FileNotFoundException {
        Response response = this.getRealization().save(request);
        return response;
    }*/
}
