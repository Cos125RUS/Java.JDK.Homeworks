package org.example;

import java.util.List;

public class MessageHandler {
    private ServerUI server;
    private List<ClientUI> clients;

    public MessageHandler(List<ClientUI> clients) {
        this.clients = clients;
    }

//        TODO: поменять отдельные эддеры на эддер общего класса
    public void addServer(ServerUI server){
        this.server = server;
    }

    public void addClient(ClientUI newClient) {
        clients.add(newClient);
    }

    public void newMessage(String message) {
        server.newMessage(message);
        for (ClientUI client: clients) {
            client.newMessage(message);
        }
    }
}
