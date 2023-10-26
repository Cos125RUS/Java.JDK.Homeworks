package org.example;

import java.util.Random;

public class Philosopher extends Thread {
    private String name;
    private Fork left;
    private Fork right;
    private int countEat;
    private boolean finished;
    private Random random;

    public Philosopher(String name, Fork left, Fork right) {
        this.name = name;
        this.left = left;
        this.right = right;
        countEat = 0;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            sleep(random.nextLong(100, 2000));
            while (countEat < 3) {
                if (!left.isUsing() && !right.isUsing()) {
                    eating();
                    countEat++;
                } else {
                    System.out.println(name + " и рад бы покушать, да вилок нет. Занята вилка "
                            + (left.isUsing() ? left : "")
                            + ((left.isUsing() && right.isUsing()) ? " и " : "")
                            + (right.isUsing() ? right : ""));
                    sleep(random.nextLong(1000, 3000));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " наелся до отвала");
        finished = true;
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

    public boolean isFinished() {
        return finished;
    }
}
