package com.company.supply;

import com.company.parts.Engine;
import com.company.store.Store;

public class EngineSupply {
    private final Thread thread;
    private int count;
    private int delay;
    public EngineSupply(Store<Engine> storage, int time){
        delay = time;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int current_id = 1;
                while(!Thread.interrupted()){
                    try {
                        Engine thing = new Engine(current_id);
                        storage.storeAdd(thing);
                        current_id++;
                        count++;
                        Thread.sleep(delay);
                    }catch (InterruptedException e){
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    public void terminate(){thread.interrupt();}

    public int getCount(){return count;}

    public void setDelay(int time) {delay = time;}
}
