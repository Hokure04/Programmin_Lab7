package org.example.exceptions;

public class ConnectionToDBException extends Exception{
    public ConnectionToDBException(String message) {
        super(message);
    }
}
