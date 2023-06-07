package org.example;

import org.example.managers.*;
/**
 * Стартовый класс
 */
public class Main {
    /**
     * Стартовый метод
     */
    public static void main(String[] args) {

        QueriesHandler queriesHandler = new QueriesHandler();

        ConnectManager connectManager = new ConnectManager(queriesHandler);
        connectManager.activePooling();
    }
}