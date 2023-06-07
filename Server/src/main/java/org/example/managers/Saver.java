package org.example.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.common.exceptions.NoAccessToTheFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*public class Saver {
    private static final Logger log = LogManager.getLogger("logfile.txt");
    long delay = 10*100;
    LoopTask task;
    Timer timer = new Timer("TaskName");
    public Saver(FileHandler fileHandler) {
        this.task = new LoopTask(fileHandler);
    }

    public void waiting(){
        timer.cancel();
        timer = new Timer("TaskName");
        Date executionDate = new Date();
        timer.schedule(task,executionDate,delay);
    }

    private class LoopTask extends TimerTask{
        private FileHandler fileHandler;
        public LoopTask(FileHandler fileHandler) {
            this.fileHandler = fileHandler;
        }
        public void run()  {
            try {
                this.fileHandler.serial();
            } catch (NoAccessToTheFileException e) {
               log.error("Ошибка: файл не доступен");
            } catch (FileNotFoundException e) {
                log.error("Ошибка: файл не найден");
            }catch (NullPointerException e){
                log.error("Путь к файлу передан не был невозможно сохранить коллекцию");
            }
        }
    }
}*/
