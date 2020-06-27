package com.company.store;

import com.company.Main;
import com.company.parts.Car;

import java.awt.*;
import java.io.FileInputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Dealer {
    Logger logger;
    private int delay;
    private final Thread thread;



    public Dealer (int time, int dealerId, CarStore carStore){
        delay = time;
        thread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Car car = carStore.getStore();
                    try(FileInputStream ins = new FileInputStream("C:\\Users\\zatol\\IdeaProjects\\CarFactory\\src\\com\\company\\log")){ //полный путь до файла с конфигами
                        LogManager.getLogManager().readConfiguration(ins);
                        logger = Logger.getLogger(Main.class.getName());
                        if (Main.logSale){
                            logger.log(Level.INFO, " Dealer:" + dealerId + " Car: " + car.getPartId()
                                    + "(Body: " + car.getBody().getPartId() + " Engine: " + car.getEngine().getPartId()+
                                    " Accessories: " +car.getAccessories().getPartId());

                        }
                    }catch (Exception ignore){
                        ignore.printStackTrace();
                    }
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        thread.start();
    }
    public void terminate() {
        thread.interrupt();
    }

    public void setDelay(int time) {
        delay = time;
    }
}
