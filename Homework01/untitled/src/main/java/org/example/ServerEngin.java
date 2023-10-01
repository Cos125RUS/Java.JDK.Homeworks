package org.example;

import java.util.ArrayList;

public class ServerEngin {
    private MessageHandler mh;

    public void run() {
        this.mh = new MessageHandler(new ArrayList<ClientUI>());
        ServerUI serverUI = new ServerUI(mh);
        mh.addServer(serverUI);


//        Имитация добавления нового клиента
        new ClientUI(this);
//        newClient();
    }

    private void newClient(ClientUI client) {
        mh.addClient(client);
    }

    public MessageHandler authorize(String IPValue, String PortValue, String LoginValue, String PassValue,
                                    ClientUI client) {
//        TODO: Добавить процесс авторизации на сервере
        newClient(client);
        System.out.println("Add new client");
        return mh;
    }
}
