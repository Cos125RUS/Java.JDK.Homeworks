package org.example;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private String name;
    private Fork left;
    private Fork right;
    private int countEat;
    private Random random;
    CountDownLatch cdl;

    public Philosopher(String name, Fork left, Fork right, CountDownLatch cdl) {
        this.name = name;
        this.left = left;
        this.right = right;
        this.cdl = cdl;
        countEat = 0;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            sleep(random.nextLong(100, 2000));
            while (countEat < 3) {
                synchronized (this) {
                    if (!left.isUsing() && !right.isUsing()) {
                        eating();
                        countEat++;
                    } else {
//                    System.out.println(name + " и рад бы покушать, да вилок нет. Занята вилка "
//                            + (left.isUsing() ? left : "")
//                            + ((left.isUsing() && right.isUsing()) ? " и " : "")
//                            + (right.isUsing() ? right : ""));
                        sleep(random.nextLong(1000, 3000));
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " наелся до отвала");
        cdl.countDown();
    }

    private void eating() throws InterruptedException {
        changeForkOptions();
        System.out.println(name + " уплетает вермишель, используя вилки: " + left
                + " и " + right);
        sleep(random.nextLong(3000, 6000));
        changeForkOptions();
        System.out.println(name + " покушал, можно и помыслить. " +
                "Не забыв при этом вернуть вилки " + left + " и " + right);
        sleep(random.nextLong(3000, 6000));
    }

    private synchronized void changeForkOptions() {
        left.setUsing(!left.isUsing());
        right.setUsing(!right.isUsing());
    }
}
