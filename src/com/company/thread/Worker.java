package com.company.thread;

import com.company.parts.*;

public class Worker implements Runnable {
    private final ThreadPool threadPool;

    public Worker(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Request request = threadPool.getRequest();
                Accessories accessories = threadPool.accessoriesStore.getStore();
                Body body = threadPool.bodyStore.getStore();
                Engine engine = threadPool.engineStore.getStore();
                Car car = new Car(threadPool.carId, accessories, body, engine);
                threadPool.carStore.storeAdd(car);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
