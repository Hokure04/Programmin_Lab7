package org.example.common.exceptions;

/**
 * Класс ошибки пустого ввода
 */
public class EmptyValueException extends Exception{
    public EmptyValueException(String message) {
        super(message);
    }
}
