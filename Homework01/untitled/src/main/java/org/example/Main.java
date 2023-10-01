package org.example;

public class Main {
    public static void main(String[] args) {
        ServerEngin server = new ServerEngin();

        new ClientUI(server);
        }
}