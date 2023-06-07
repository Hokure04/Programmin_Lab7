package org.example.common.exceptions;

/**
 * Класс ошибки отсутствия прав на файл
 */
public class NoAccessToTheFileException extends Exception{
    public NoAccessToTheFileException(String message) {
        super(message);
    }
}