package org.example;

import java.util.Arrays;
import java.util.List;

public class Pair<T, U> {
    T t;
    U u;

    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    public T getFirst() {
        return t;
    }

    public U getSecond() {
        return u;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "t=" + t + "(" + t.getClass().getSimpleName() + ")" +
                ", u=" + u +"(" + u.getClass().getSimpleName() + ")" +
                '}';
    }

    public static void main(String[] args) {
        Pair<String, Double> product = new Pair<>("Samsung Galaxy A73", 34000.0);
        System.out.println(product);
        Pair<List<String>, ArrCompare.Cat> cat = new Pair<>(
                Arrays.asList("Привит от чумки", "Приучен к горшку"),
                new ArrCompare.Cat(3, "Кусака"));
        System.out.println(cat);

    }
}
