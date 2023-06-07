package org.example.common.exceptions;
/**
 * Класс ошибки создания объекта класса
 */
public class TroubleObjectCreationException extends Exception{
    public TroubleObjectCreationException(String message) {
        super(message);
    }
}
