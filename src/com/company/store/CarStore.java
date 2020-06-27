package com.company.store;

import com.company.Main;
import com.company.parts.Car;

public class CarStore extends Store<Car> {
    public CarStore(int maxQueue) {
        super(maxQueue);
    }
    public Car getStore() throws InterruptedException{
        synchronized (Main.controller){
            Main.controller.requestActive++;
            Main.controller.notify();
        }
        return super.getStore();
    }

}
