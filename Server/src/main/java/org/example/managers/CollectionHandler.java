package org.example.managers;

import org.example.common.data.Vehicle;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * Класс для работы с коллекцией
 */
public class CollectionHandler<T> {
    private Map<Integer, T> collection;
    private final LocalDate createDate;
    //private static Integer maxId = 0;

    private String pathToFile;
    public CollectionHandler() {
        this.collection = new LinkedHashMap<Integer, T>();
        this.createDate = LocalDate.now();
    }
    /**
     * Получить дату создания коллекции
     */
    public LocalDate getCreateDate() {
        return createDate;
    }
    /*public void setObjectId(Vehicle element) {
        maxId += 1;
        element.setId(maxId);
    }*/
    /**
     * Получить ссылку на коллекцию
     */
    public Map<Integer, T> getCollection() {
        return this.collection;
    }

    /**
     * Пересоздать коллекцию
     */
    public void setNewCollection() {
        synchronized (this.collection) {
            this.collection = new LinkedHashMap<Integer, T>();
        }
    }

    /*public void setMaxId(Integer maxId) {
        CollectionHandler.maxId = maxId;
    }*/

    /**
     * Заполнить коллекцию
     * @param col коллекция, которая будет записана
     */
    public void setCollection(LinkedHashMap<Integer, T> col) {
        synchronized (this.collection) {
            this.collection = col;
        }
    }
    /**
     * Добавить элемент в коллекцию по ключу
     * @param key ключ нового элемента
     * @param element объект нового элемента
     */
    public void addElement(int key, T element) {
        synchronized (this.collection) {
            this.collection.put(key, element);
        }
    }
    /**
     * Удалить элемент из коллекции по ключу
     * @param key ключ удаляемого элемента
     */
    public void removeElement(int key) {
        synchronized (this.collection) {
            this.collection.remove(key);
        }
    }
    /**
     * Заменить объект коллекции по ключу
     * @param key ключ заменяемого элемента
     * @param element объект нового элемента
     */
    public void replaceElement(int key, T element) {
        synchronized (this.collection) {
            this.collection.replace(key, element);
        }
    }
    /**
     * Создать Id для нового элемента коллекции
     */
}
