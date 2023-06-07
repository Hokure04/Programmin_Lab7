package org.example.common.requests;

import java.io.Serializable;
import java.util.Map;

public class DescCommand implements Serializable {
    private String title;
    private String args;
    private String description;
    private int countArgs;
    private Map<Integer, String> typeArgs;
    private Map<Integer, String> typeCompositArgs;
    private TypesRequest typeRequest;

    public DescCommand(String title, String args, String description, int countArgs, Map<Integer, String> typeArgs, Map<Integer, String> typeCompositArgs, TypesRequest typeRequest) {
        this.title = title;
        this.args = args;
        this.description = description;
        this.countArgs = countArgs;
        this.typeArgs = typeArgs;
        this.typeCompositArgs = typeCompositArgs;
        this.typeRequest = typeRequest;
    }

    public int getCountArgs() {
        return countArgs;
    }

    public String getDescription() {
        return description;
    }

    public Map<Integer, String> getTypeArgs() {
        return typeArgs;
    }

    public String getTitle() {
        return title;
    }

    public Map<Integer, String> getTypeCompositArgs() {
        return typeCompositArgs;
    }

    public TypesRequest getTypeRequest() {
        return typeRequest;
    }
}
