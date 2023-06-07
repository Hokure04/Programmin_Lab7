package org.example.common.responses;

import org.example.common.data.Vehicle;

import java.io.Serializable;
import java.util.Collection;

public class ResponseResult extends BaseResponse implements Serializable {
    private int status;
    private String error;
    private String value;

    public ResponseResult(int status, String error, TypesResponse typeResponse, String value) {
        this.status = status;
        this.error = error;
        this.value = value;
        this.typeResponse = typeResponse;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getValue() {
        return value;
    }
}
