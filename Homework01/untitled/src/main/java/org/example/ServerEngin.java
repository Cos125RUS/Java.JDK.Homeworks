package org.example;

import java.util.ArrayList;

public class ServerEngin {
    private MessageHandler mh;

    public void run() {
        this.mh = new MessageHandler(new ArrayList<ClientUI>());
        ServerUI serverUI = new ServerUI(mh);
        mh.addServer(serverUI); // Заменить после добавления эддера базового класса

//        Имитация добавления нового клиента
        newClient();
    }

    private void newClient() {
        mh.addClient(new ClientUI(mh));
    }
}
