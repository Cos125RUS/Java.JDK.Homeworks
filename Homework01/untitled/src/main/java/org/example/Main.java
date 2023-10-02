package org.example;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        ServerEngin server = new ServerEngin();

        new ClientUI(server);

        }
}