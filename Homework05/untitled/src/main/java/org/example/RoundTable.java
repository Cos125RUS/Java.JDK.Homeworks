package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class RoundTable extends Thread {
    private final int PHILOSOPHER_COUNT = 5;
    Fork[] forks;
    Philosopher[] philosophers;
    CountDownLatch cdl;


    public RoundTable() {
        this.forks = new Fork[PHILOSOPHER_COUNT + 2];
        this.philosophers = new Philosopher[PHILOSOPHER_COUNT + 1];
        cdl = new CountDownLatch(PHILOSOPHER_COUNT);
        init();
    }

    @Override
    public void run() {
        System.out.println("Заседание макаронных мудрецов объявляется открытым");
        try {
            thinkingProcess();
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Все философы накушались");
    }

    private void init() {
        for (int i = 1; i < PHILOSOPHER_COUNT + 1; i++) {
            forks[i] = new Fork(i);
        }
        forks[PHILOSOPHER_COUNT + 1] = forks[1];

        for (int i = 1; i < PHILOSOPHER_COUNT + 1; i++) {
            philosophers[i] = new Philosopher("Philosopher №" + i, forks[i], forks[i + 1], cdl);
        }
    }

    private void thinkingProcess() {
        for (int i = 1; i < PHILOSOPHER_COUNT + 1; i++) {
            philosophers[i].start();
        }
    }
}
