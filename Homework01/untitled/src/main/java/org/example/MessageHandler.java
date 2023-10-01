package org.example;

import java.util.List;

public class MessageHandler {
    private List<UI> members;

    public MessageHandler(List<UI> clients) {
        this.members = clients;
    }

    public void addMember(UI newClient) {
        members.add(newClient);
//        TODO: Добавить логин к сообщению
    }

    public void newMessage(String message) {
        for (UI client: members) {
            client.newMessage(message);
        }
    }
}
