package org.example;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    private List<UI> members;
    private boolean isWork;

    public MessageHandler() {
        this.members = new ArrayList<UI>();
        this.isWork = false;
    }

    public void addMember(UI newClient) {
        members.add(newClient);
//        TODO: Добавить логин к сообщению
    }

    public void run(){
        isWork = true;
    }

    public void stop(){
        isWork = false;
    }

    public void newMessage(String message) {
//        TODO: Добавить логирование
        if (isWork) {
            for (UI client : members) {
                client.newMessage(message);
            }
        }
    }
}
