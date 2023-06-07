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
    private QueriesHandlerType type;
    private ArrayList<Scanner> queue;

    public QueriesHandler() {
        this.scanner = new Scanner(System.in);
        this.type = QueriesHandlerType.CONSOLE;
        this.queue = new ArrayList<>();
    }
    /**
     * Получить тип актуальный тип обработчика
     */
    public QueriesHandlerType getType() {
        return type;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public ArrayList<Scanner> getQueue() {
        return this.queue;
    }

    /**
     * Установить тип FILE обработчику
     * @param path путь до файла
     */
    public void setFileType(String path) throws FileNotFoundException, NoAccessToTheFileException, UnknownCommandException {
        try {
            String filePath = new File("").getAbsolutePath() + "/";
            File file;
            if (String.valueOf(path.charAt(0)).equals("/")) {
                file = new File(path);
            } else {
                file = new File(filePath + path);
            }
            if (!file.canRead()) {
                throw new NoAccessToTheFileException("У программы нет права на чтение из файла");
            }
            this.scanner = new Scanner(file);
            if (this.queue.size() < 10) {
                this.queue.add(this.scanner);
            } else {
                throw new UnknownCommandException("Достигнута максимально допустимая вложенность");
            }
            this.type = QueriesHandlerType.FILE;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Путь указан неверно");
        } catch (NoAccessToTheFileException e) {
            throw new NoAccessToTheFileException(e.getLocalizedMessage());
        } catch (UnknownCommandException e) {
            throw new UnknownCommandException(e.getLocalizedMessage());
        }
    }
    public void setOldScanner() {
        this.queue.remove(queue.size() - 1);
        this.scanner = queue.get(queue.size() - 1);
    }
    /**
     * Установить тип CONSOLE обработчику
     */
    public void setConsoleType() {
        this.type = QueriesHandlerType.CONSOLE;
        this.scanner = new Scanner(System.in);
    }
    /**
     * Создать запрос на ввод
     * @param line текст запроса
     */
    public String query(String line) {
        if (this.type == QueriesHandlerType.CONSOLE) {
            System.out.println(line);
        }
        return this.scanner.nextLine().trim();
    }

    public void write(String line) {
        System.out.println(line);
    }
}
