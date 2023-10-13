package org.example;

public class Calc {
    private static <T> boolean check(T arg1, T arg2) {
        return ((arg1 instanceof Integer || arg1 instanceof Long) &&
                (arg2 instanceof Integer || arg2 instanceof Long));
    }

    public static <T extends Number> T sum(T arg1, T arg2) {
        Number res;
        if (check(arg1, arg2)) {
            res = Long.parseLong(arg1.toString()) + Long.parseLong(arg2.toString());
        } else
            res = Double.parseDouble(arg1.toString()) + Double.parseDouble(arg2.toString());
        return (T) res;
    }

    public static <T> T multiply(T arg1, T arg2) {
        Number res;
        if (check(arg1, arg2)) {
            res = Long.parseLong(arg1.toString()) * Long.parseLong(arg2.toString());
        } else
            res = Double.parseDouble(arg1.toString()) * Double.parseDouble(arg2.toString());
        return (T) res;
    }

    public static <T> T divide(T arg1, T arg2) {
        Number res;
        if (check(arg1, arg2)) {
            res = Long.parseLong(arg1.toString()) / Long.parseLong(arg2.toString());
        } else
            res = Double.parseDouble(arg1.toString()) / Double.parseDouble(arg2.toString());
        return (T) res;
    }

    public static <T> T subtract(T arg1, T arg2) {
        Number res;
        if (check(arg1, arg2)) {
            res = Long.parseLong(arg1.toString()) - Long.parseLong(arg2.toString());
        } else
            res = Double.parseDouble(arg1.toString()) - Double.parseDouble(arg2.toString());
        return (T) res;
    }

    public static void main(String[] args) {
        System.out.println(sum(2, 4));
        System.out.println(multiply(2, 2f));
        System.out.println(divide(13, 2));
        System.out.println(subtract(12, 4.2));
    }
}
