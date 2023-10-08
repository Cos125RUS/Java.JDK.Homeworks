package org.example.server;

import org.example.server.exceptions.StartServerException;
import org.example.server.exceptions.StopServerException;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<String, String> users;


    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        this.isRun = false;
        this.chat = new ArrayList<>();
        this.repo = new Repo();
        this.connects = new HashMap<>(); // TODO забирать из базы данных
        this.users = new HashMap<>();
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
                        repo.load();
                        chat = repo.getHistory();
                        users = repo.getUsers();
                    } catch (RuntimeException e) {
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

    /**
     * Добавление нового пользователя
     * @param login
     * @param userSocket
     */
    public void newUser(String login, Socket userSocket){
        connects.put(login, userSocket);
//        printLog(login);
    }

    /**
     * Печать системного лога
     * @param log
     */
    public void printLog(String log) {
        admin.printLog(log);
    }

    /**
     * Печать сообщений
     * @param message
     */
    public void printMessage(String message) {
        admin.printMessage(message);
    }

    /**
     * Запись истории сообщений
     * @param message новое сообщение
     */
    public void addToHistory(String message) {
        repo.addMessage(message);
        try {
            chat.add(message);
        } catch (RuntimeException e) {
            printLog("Ошибка записи текущего лога истории");
        }
    }

    /**
     * Отправка истории чата
     * @return
     */
    public ArrayList<String> getHistory() {
        return chat;
    }

    /**
     * Авторизация на сервере
     * @param login
     * @param password
     * @return
     */
    public boolean authorization(String login, String password) {
        if (users.containsKey(login))
            return users.get(login).equals(password);
        else {
            users.put(login, password);
            repo.addUser(login, password);
            return true;
        }
    }
}
