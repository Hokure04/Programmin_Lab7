package org.example.common.requests;

import java.io.Serializable;

public class BaseRequest implements Serializable {
    protected TypesRequest typeRequest;
    protected String command;

    public TypesRequest getTypeRequest() {
        return typeRequest;
    }

    public String getCommand() {
        return command;
    }
}
