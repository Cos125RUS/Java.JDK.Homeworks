package org.example.server;

import org.example.server.exceptions.StartServerException;
import org.example.server.exceptions.StopServerException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Движок сервера
 */
public class Server {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8888;

    private ServerSocket serverSocket;
    private final ServerView admin;
    private final DataBase repo;
    private Catcher catcher;
    private Messenger messenger;

    private boolean isRun;
    private ArrayList<String> chat;
    private HashMap<String, String> members;


    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        this.isRun = false;
        this.chat = new ArrayList<>();
        this.repo = new Repo();
        this.members = new HashMap<>();
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
                try {
                    this.serverSocket = new ServerSocket(PORT);
                    this.catcher = new Catcher(this, serverSocket);
                    this.messenger = new Messenger(this);
                    catcher.start();
                    isRun = true;
                    try {
                        repo.load();
                        chat = repo.getHistory();
                        members = repo.getUsers();
                    } catch (RuntimeException e) {
                        printLog(e.getMessage());
                    }
                    return 0;
                } catch (IOException e) {
                    printLog(e.getMessage());
                }
            } else {
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
                catcher.stopCatching(HOST, PORT);
                messenger.disconnect();
                return 0;
            } else {
                return 1;
            }
        } catch (StopServerException e) {
            printLog(e.getMessage());
        }
        return 666;
    }

    /**
     * Добавление нового пользователя в рассылку
     * @param login
     * @param password
     * @param client
     * @param inputStream
     * @param clientIn
     * @param clientOut
     * @throws IOException
     * @throws InterruptedException
     */
    public void newUser(String login, String password, Socket client,
                        InputStreamReader inputStream, BufferedReader clientIn,
                        BufferedWriter clientOut) throws IOException, InterruptedException {
        messenger.addMember(new User(messenger, login, password, client, inputStream,
                clientIn, clientOut));
        printLog("Новый юзер: " + login);

    }

    /**
     * Печать системного лога
     *
     * @param log
     */
    public void printLog(String log) {
        admin.printLog(log);
    }

    /**
     * Печать сообщений
     *
     * @param message
     */
    public void printMessage(String message) {
        admin.printMessage(message);
    }

    /**
     * Запись истории сообщений
     *
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
     *
     * @return
     */
    public ArrayList<String> getHistory() {
        return chat;
    }

    /**
     * Авторизация на сервере
     *
     * @param login
     * @param password
     * @return
     */
    public boolean authorization(String login, String password) {
        if (members.containsKey(login))
            return members.get(login).equals(password);
        else {
            members.put(login, password);
            repo.addUser(login, password);
            return true;
        }
    }

    public boolean isRun() {
        return isRun;
    }
}
