package com.company.store;

import com.company.Main;
import com.company.parts.Request;

public class Controller {
    private static volatile Thread thread;
    public int requestActive = 0;

    public Controller() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (thread == null) {
                        synchronized (Controller.class) {
                            if (requestActive == 0) {
                                try {
                                    Controller.this.wait();
                                } catch (InterruptedException e) {
                                    break;
                                }
                            }
                            requestActive--;
                        }
                        Main.threadPool.addRequest(new Request());
                    }
                }
            }
        });
        thread.start();
    }

    public void terminate() {
        thread.interrupt();
    }
}
