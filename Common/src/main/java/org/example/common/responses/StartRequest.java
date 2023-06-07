package org.example.common.responses;

import org.example.common.requests.DescCommand;
import org.example.common.responses.BaseResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StartRequest extends BaseResponse implements Serializable {
    private Map<String, DescCommand> commands = new HashMap<>();

    public void addCommand(DescCommand command) {
        this.commands.put(command.getTitle(), command);
    }

    public Map<String, DescCommand> getCommands() {
        return commands;
    }
}
