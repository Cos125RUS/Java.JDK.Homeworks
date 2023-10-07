package org.example.server;

import org.example.server.exceptions.StartServerException;
import org.example.server.exceptions.StopServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Движок сервера
 */
public class Server {
    private static final int PORT = 8888;

    private HashMap<String, Socket> connects;
    private final ServerView admin;
    private final DataBase repo;
    private Catcher catcher;

    private boolean isRun;
    private ArrayList<String> chat;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        this.isRun = false;
        this.chat = new ArrayList<>();
        this.repo = new Repo();
        this.connects = new HashMap<>(); // TODO забирать из базы данных
        this.admin = new ServerUI(this);
    }

    /**
     * Старт сервера
     *
     * @return код выполнения операции
     */
    public int startServer() {
        try {
            if (!isRun) {
                    this.catcher = new Catcher(this, PORT);
                    catcher.start();
                    isRun = true;
                    try {
                        chat.add(repo.getHistory());
                    } catch (RuntimeException | IOException e) {
                        printLog(e.getMessage());
                    }
                    return 0;
            } else if (isRun) {
                return 1;
            }
        } catch (StartServerException e) {
            printLog(e.getMessage());
        }
        return 666;
    }

    /**
     * Остановка сервера
     *
     * @return код выполнения операции
     */
    public int stopServer() {
        try {
            if (isRun) {
                isRun = false;
                //TODO добавить оповещение пользователей
                return 0;
            } else if (!isRun) {
                return 1;
            }
        } catch (StopServerException e) {
            printLog(e.getMessage());
        }
        return 666;
    }

    public void newUser(String login, Socket userSocket){
        connects.put(login, userSocket);
//        printLog(login);
    }

    public void printLog(String log) {
        admin.printLog(log);
    }

    public void printMessage(String message) {
        admin.printMessage(message);
    }
}
