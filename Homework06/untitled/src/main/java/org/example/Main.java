package org.example;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.HashMap;
import java.util.Random;

public class Main {
    public static HashMap<Integer, Integer> results = new HashMap<>();
    public static Random random = new Random();

    public static void main(String[] args) {
        game(1000);
        printStatistic();
    }

    public static void game(int quantity) {
        for (int i = 0; i < quantity; i++) {
            String[] doors = {"Коза", "Коза", "Коза"};
            doors[random.nextInt(0, 3)] = "АВТОМОБИЛЬ!";
            int userChoice = random.nextInt(0, 3), openDoor = -1;
            System.out.println("Игрок выбрал дверь номер " + userChoice);
            for (int j = 0; j < 3; j++) {
                if (j != userChoice && !doors[j].equals("АВТОМОБИЛЬ!")) {
                    openDoor = j;
                    System.out.println("Ведущий показал козу за дверью номер " + openDoor);
                    break;
                }
            }
            userChoice = 3 - userChoice - openDoor;
            System.out.println("Монти предложил поменять дверь и игрок выбрал дверь номер " + userChoice);
            if (doors[userChoice].equals("АВТОМОБИЛЬ!")) {
                System.out.println("Игрок выиграл АВТОМОБИЛЬ!!!");
                results.put(i, 1);
            } else {
                System.out.println("Игрок ушёл домой с козой. Теперь будет кормить её, доить и выгуливать");
                results.put(i, 0);
            }
            System.out.println();
        }
    }

    public static void printStatistic() {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (int i = 0; i < results.size(); i++) {
            descriptiveStatistics.addValue(results.get(i));
        }
        System.out.println("-------------------------------------------------");
        System.out.printf("Количество выигранных автомобилей: %.0f (%.1f%%)\n",
                descriptiveStatistics.getSum(),
                (descriptiveStatistics.getSum() / results.size() * 100));
        System.out.printf("Количество приручённых коз: %.0f (%.1f%%)\n\n",
                (results.size() - descriptiveStatistics.getSum()),
                ((results.size() - descriptiveStatistics.getSum()) / results.size() * 100));
    }
}