package com.company.store;

import java.util.ArrayDeque;
import java.util.Queue;

public class Store <T>{
    private Queue<T> queue;
    private int maxQueue;

    public Store(int maxQueue){
        queue = new ArrayDeque<>();
        this.maxQueue=maxQueue;
    }
    public synchronized void storeAdd(T item) throws InterruptedException {
        while (queue.size()>=maxQueue)
            wait();
        queue.add(item);
        notifyAll();
    }
    public int storageSize(){
        return queue.size();
    }

    public synchronized T getStore() throws InterruptedException {
        while (queue.isEmpty())
            wait();
        T obj = queue.poll();
        notifyAll();
        return obj;
    }

}
