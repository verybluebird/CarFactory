package com.company.thread;

import com.company.parts.Accessories;
import com.company.parts.Body;
import com.company.parts.Engine;
import com.company.parts.Request;
import com.company.store.CarStore;
import com.company.store.Store;

import java.util.ArrayDeque;
import java.util.Queue;

public class ThreadPool {
    Thread[] threads;
    Store<Accessories> accessoriesStore;
    Store<Engine> engineStore;
    Store<Body> bodyStore;
    Queue<Request> queue;
    CarStore carStore;
    int carId;

    public ThreadPool(int store) {
        queue = new ArrayDeque<>();//двунаправоенная очередь
        threads = new Thread[store];
        for (int i = 0; i < store; i++) {
            threads[i] = new Thread(new Worker(this));
            threads[i].start();
        }
    }

    public synchronized void addRequest(Request request){
        queue.add(request);
        notifyAll();
    }

    public synchronized Request getRequest() throws InterruptedException {
        while (queue.isEmpty())
            wait();
        return queue.poll();//возвращает с удалением элемент из начала очереди. Если очередь пуста, возвращает значение null
    }

    public synchronized int getCarId(){
        return carId;
    }

    public void terminate(){
        for (Thread thread: threads){
            thread.interrupt();
        }
    }

}
