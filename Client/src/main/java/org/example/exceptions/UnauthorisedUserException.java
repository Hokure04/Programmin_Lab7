package org.example.exceptions;

public class UnauthorisedUserException extends Exception{
    public UnauthorisedUserException(String message) {
        super(message);
    }
}
