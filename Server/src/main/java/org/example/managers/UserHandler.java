package org.example.managers;

import java.util.TreeSet;

public class UserHandler {
    private TreeSet<String> users = new TreeSet<>();

    public void addUser(String login){
        this.users.add(login);
    }

    public boolean checkUserLoginExist(String userName){
        return this.users.contains(userName);
    }

    public void setNewCollection() {
        this.users = new TreeSet<>();
    }
}
