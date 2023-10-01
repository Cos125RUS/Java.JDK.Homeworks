package org.example;

public class ServerEngin {

    public void run() {
        ServerUI serverUI = new ServerUI();
        newClient();
    }

    private void newClient() {
        new ClientUI();
    }
}
