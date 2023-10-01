package org.example;

import java.util.ArrayList;

public class ServerEngin {
    private MessageHandler mh;
    private boolean isRun;

    public ServerEngin() {
        this.isRun = false;
        this.mh = new MessageHandler();
        ServerUI serverUI = new ServerUI(this, mh);
        mh.addMember(serverUI);
//        TODO: Добавить загрузку списка мемберов
//        TODO: Добавить загрузку чата
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

    private void newClient(ClientUI client) {
        mh.addMember(client);
    }

    public MessageHandler authorize(String IPValue, String PortValue, String LoginValue, String PassValue,
                                    ClientUI client) {
//        TODO: Добавить процесс авторизации на сервере
        newClient(client);
        return mh;
    }
}
