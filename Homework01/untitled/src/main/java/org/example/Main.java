package org.example;

public class Main {
//    TODO: сделать базовый класс для сервера и клиента
    public static void main(String[] args) {
        ServerEngin server = new ServerEngin();

        new ClientUI(server);
        }
}