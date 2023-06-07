package org.example.common.exceptions;
/**
 * Класс ошибки неизвестной команды
 */
public class UnknownCommandException extends Exception{
    public UnknownCommandException(String message) {
        super(message);
    }
}
