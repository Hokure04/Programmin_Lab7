package org.example.common.data;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private final String password;
    private final String login;

    public User(String login, String password){
        this.password = password;
        this.login = login;
    }

    public String getPassword(){
        return password;
    }

    public String getLogin(){
        return login;
    }
}
