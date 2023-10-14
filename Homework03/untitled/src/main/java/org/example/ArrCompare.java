package org.example;

public class ArrCompare {
    public static <T> boolean compareArrays(T[] arr1, T[] arr2) {
        if (arr1.length != arr2.length)
            return false;
        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equals(arr2[i]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] strings1 = {"One", "Two", "Three"};
        String[] strings2 = {"One", "Two", "Three"};
        String[] strings3 = {"One", "Two", "Five"};
        System.out.println(compareArrays(strings1, strings2));
        System.out.println(compareArrays(strings1, strings3));
        System.out.println();

        Integer[] integers1 = {1,2,3};
        Integer[] integers2 = {1,2,3};
        Integer[] integers3 = {1,2,5};
        System.out.println(compareArrays(integers1, integers2));
        System.out.println(compareArrays(integers1, integers3));
        System.out.println();

        Cat barsik = new Cat(3, "Barsik");
        Cat murzik = new Cat(3, "Murzik");
        Cat pushok = new Cat(3, "Pushok");
        Cat[] cats1 = {barsik, murzik, pushok};
        Cat[] cats2 = {barsik, murzik, pushok};
        Cat[] cats3 = {barsik, murzik};
        System.out.println(compareArrays(cats1, cats2));
        System.out.println(compareArrays(cats1, cats3));
        System.out.println();
    }

    static class Cat implements Comparable{
        int age;
        String name;

        public Cat(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public int compareTo(Object o) {
            Cat cat = (Cat) o;
            return Integer.compare(age, cat.age);
        }

        @Override
        public String toString() {
            return "name: " + name + " лет:" + age;
        }
    }
}
