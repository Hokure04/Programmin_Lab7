package org.example.common.responses;

import java.io.Serializable;

public abstract class BaseResponse implements Serializable {
    protected TypesResponse typeResponse;

    public TypesResponse getTypeResponse() {
        return typeResponse;
    }
}
