package org.example.managers;

import org.example.common.exceptions.NoAccessToTheFileException;
import org.example.common.exceptions.UnknownCommandException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс для запроса ввода
 */
public class QueriesHandler {
    private Scanner scanner;

    public QueriesHandler() {
        this.scanner = new Scanner(System.in);
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Создать запрос на ввод
     * @param line текст запроса
     */
    public String query(String line) {
        System.out.println(line);
        return this.scanner.nextLine().trim();
    }

    public void write(String line) {
        System.out.println(line);
    }
}
