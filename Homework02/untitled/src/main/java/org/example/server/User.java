package org.example.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class User extends Thread {
    private Socket client;
    private BufferedReader clientIn;
    private BufferedWriter clientOut;
    private InputStreamReader inputStream;
    private String login;
    private String password;
    private String message;
    private Messenger messenger;
    private boolean isRun;


    public User(Messenger messenger, String login, String password, Socket client,
                InputStreamReader inputStream, BufferedReader clientIn, BufferedWriter clientOut) {
        this.messenger = messenger;
        this.client = client;
        this.clientIn = clientIn;
        this.clientOut = clientOut;
        this.inputStream = inputStream;
        this.login = login;
        this.password = password;
        this.message = "";
    }


    @Override
    public void run() {
        this.isRun = true;
        while (client.isConnected() && isRun) {
            try {
                if (inputStream.ready()) {
                    message = clientIn.readLine();
                    messenger.distribution(message);
                }
                sleep(100);
            } catch (IOException | InterruptedException e) {
                messenger.printLog(e.getMessage());
            }
        }
//        messenger.distribution("Соединение с сервером разорвано");
    }

    public void sendMessage(String message) throws IOException {
        clientOut.write(message + "\n");
        clientOut.flush();
    }

    public void disconnect() {
        isRun = false;
        try {
            sleep(100);
            client.close();
            clientIn.close();
            clientOut.close();
            inputStream.close();
        } catch (InterruptedException | IOException e) {
            messenger.printLog(e.getMessage());
        }
    }
}
