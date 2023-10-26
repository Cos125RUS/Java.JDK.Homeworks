package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoundTable extends Thread {
    private final int PHILOSOPHER_COUNT = 5;
    Fork[] forks;
    Philosopher[] philosophers;


    public RoundTable() {
        this.forks = new Fork[PHILOSOPHER_COUNT + 2];
        this.philosophers = new Philosopher[PHILOSOPHER_COUNT + 1];
        init();
    }

    @Override
    public void run() {
        try {
            System.out.println("Заседание макаронных мудрецов объявляется открытым");
            thinkingProcess();
            boolean inProcess = true;
            List<Integer> philosopherId = new CopyOnWriteArrayList<>();
            for (int i = 1; i < PHILOSOPHER_COUNT + 1; i++) {
                philosopherId.add(i);
            }
            while (inProcess) {
                if (philosopherId.isEmpty())
                    inProcess = false;
                for (Integer id : philosopherId) {
                    if (philosophers[id].isFinished()) {
                        philosopherId.remove(id);
                    }
                }
                sleep(1000);
            }
            System.out.println("Все философы накушались");
        } catch (InterruptedException e) {
            throw new RuntimeException("Заседание прервано ввиду непредвиденных обстоятельств");
        }
    }

    private void init() {
        for (int i = 1; i < PHILOSOPHER_COUNT + 1; i++) {
            forks[i] = new Fork(i);
        }
        forks[6] = forks[1];

        for (int i = 1; i < PHILOSOPHER_COUNT + 1; i++) {
            philosophers[i] = new Philosopher("Philosopher №" + i, forks[i], forks[i + 1]);
        }
    }

    private void thinkingProcess() {
        for (int i = 1; i < PHILOSOPHER_COUNT + 1; i++) {
            philosophers[i].start();
        }
    }
}
