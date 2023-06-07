package org.example.exceptions;

public class MissingCommandException extends Exception{
    public MissingCommandException(String message) {
        super(message);
    }
}
