package org.example.server;

import java.io.IOException;
import java.util.ArrayList;

public class Messenger extends Thread implements Distribute{
    private ArrayList<User> users;
    private final Server server;
    private User newUser;

    public Messenger(Server server) {
        this.users = new ArrayList<>();
        this.server = server;
    }


    @Override
    public void run() {

    }

    @Override
    public void addMember(User user) {
        this.newUser = user;
        checkNewMember();
    }

    @Override
    public void checkNewMember() {
        users.add(newUser);
        newUser.start();
    }

    @Override
    public void printMessage(String message) {
        server.printMessage(message);
    }

    @Override
    public void addToHistory(String message) {
        server.addToHistory(message);
    }

    @Override
    public void printLog(String log) {
        server.printLog(log);
    }

    @Override
    public void distribution(String message) {
        addToHistory(message);
        for (User user: users) {
            try {
                user.sendMessage(message);
            } catch (IOException e) {
                printLog(e.getMessage());
            }
        }
    }
}
