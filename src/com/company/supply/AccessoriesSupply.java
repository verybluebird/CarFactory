package com.company.supply;

import com.company.parts.Accessories;
import com.company.store.Store;

public class AccessoriesSupply {
    private final Thread thread;
    private int delay;
    private int count;

    public AccessoriesSupply(Store<Accessories> accessoriesStore, int time, int id) {
        delay = time;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int current_id = id;
                while (!Thread.interrupted()) {
                    try {
                        Accessories thing = new Accessories(current_id);
                        accessoriesStore.storeAdd(thing);
                        current_id++;
                        count++;
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    public void terminate() {
        thread.interrupt();
    }

    public int getCountDelivery() {
        return count;
    }

    public void setDelay(int time) {
        delay = time;
    }

}
