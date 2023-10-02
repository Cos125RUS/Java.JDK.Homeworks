package org.example;

import java.io.*;
import java.util.ArrayList;

public class ServerEngin {
    private static final String chatHistoryPath = "server/ChatHistory.txt";
    private static final String clientListPath = "server/ClientList.txt";
    private final MessageHandler mh;
    private boolean isRun;

    public ServerEngin() {
        this.isRun = false;
        this.mh = new MessageHandler();
        ServerUI serverUI = new ServerUI(this, mh);
        mh.addMember(serverUI);
//        TODO: Добавить отправку истории чата
//        TODO: Добавить загрузку списка мемберов
    }


    public int run() {
//            Имитация запуска сервера
        try {
            if (!isRun) {
                isRun = true;
                mh.run();
                return 0;
            } else if (isRun) {
                return 1;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
        return 2;
    }

    public int stop() {
//            Имитация остановки сервера
        try {
            if (isRun) {
                isRun = false;
                mh.stop();
                return 0;
            } else if (!isRun) {
                return 1;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
        return 2;
    }

    public boolean authorize(String IPValue, String PortValue, String LoginValue, String PassValue,
                             ClientUI client) {
//        TODO: Добавить процесс авторизации на сервере
        if (isRun) {
            newClient(client);
            return true;
        } else
            return false;
    }

    private void newClient(ClientUI client) {
        mh.addMember(client);
    }

    public void newMessage(String message) {
//        TODO: Добавить обработку исключений
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chatHistoryPath, true))) {
            bw.append(String.format(message + '\n'));
            bw.flush();
            mh.newMessage(message);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getHistory() {
        String history = loadHistory();
        mh.newMessage(history);
    }

    private String loadHistory() {
//        TODO: Добавить обработку исключений
        String history = "";
        try (BufferedReader br = new BufferedReader(new FileReader(chatHistoryPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                history += line + '\n';
            }
            return history;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return history;
        }
    }
}
