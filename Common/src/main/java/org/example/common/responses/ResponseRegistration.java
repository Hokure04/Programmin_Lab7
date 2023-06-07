package org.example.common.responses;

import org.example.common.data.User;

import java.io.Serializable;

public class ResponseRegistration extends BaseResponse implements Serializable {
    private int status;
    private String value;
    private String error;
    private User user;

    public ResponseRegistration(int status, String error, String value, TypesResponse typeResponse, User user) {
        this.status = status;
        this.value = value;
        this.error = error;
        this.user = user;
        this.typeResponse = typeResponse;
    }

    public String getValue() {
        return value;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }
}
