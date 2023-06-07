package org.example.commands;

import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.TypesRequest;
import org.example.common.responses.BaseResponse;
import org.example.util.TypeAccess;

/**
 * Класс команды вывода элементов коллекции, больших заданных по типу топлива
 */
public class FilterGreaterThanFuelTypeCommand extends Command {
    /**
     * @param realization объект realization
     */
    public FilterGreaterThanFuelTypeCommand(Realization realization) {
        super("filter_greater_than_fuel_type", "fuelType", "Вывод элементов, значение fuelType которых больше заданного. fuelType является одной из заранее определенных констант", 0, new String[]{}, new String[]{"FUELTYPE"}, TypesRequest.COMMAND, TypeAccess.AUTHORISED, realization);
    }

    @Override
    public BaseResponse execute(BaseRequest request) {
        BaseResponse response = this.getRealization().filterGreaterThanFuelType(request);
        return response;
    }
}
