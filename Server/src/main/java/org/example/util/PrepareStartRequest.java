package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.commands.Command;
import org.example.common.requests.DescCommand;
import org.example.common.responses.StartRequest;
import org.example.managers.CommandHandler;

import java.util.HashMap;
import java.util.Map;

public class PrepareStartRequest {
    private static final Logger log = LogManager.getLogger("logfile.txt");
    public static StartRequest startPreparing(CommandHandler commandHandler) {
        StartRequest startRequest = new StartRequest();
        log.info("Стартовый запрос: "+startRequest);
        DescCommand command;
        for (Command i : commandHandler.getCommands().values()) {
            Map<Integer, String> typeArgs = new HashMap<>();
            int count = 0;
            for (String z : i.getTypeArgs()) {
                typeArgs.put(count, z);
                count += 1;
            }
            Map<Integer, String> typeCompositArgs = new HashMap<>();
            count = 0;
            for (String z : i.getTypeCompositArgs()) {
                typeCompositArgs.put(count, z);
                count += 1;
            }
            command = new DescCommand(i.getName(), i.getArgs(), i.getDescription(), i.getCountArgs(), typeArgs, typeCompositArgs, i.getTypeRequest());
            startRequest.addCommand(command);
        }
        return startRequest;
    }
}
