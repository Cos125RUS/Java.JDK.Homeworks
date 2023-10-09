package org.example.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class User extends Thread{
    private Socket client;
    private BufferedReader clientIn;
    private BufferedWriter clientOut;
    private InputStreamReader inputStream;
    private String login;
    private String password;
    private String message;
    private Messenger messenger;


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
        while (client.isConnected()) {
            try {
                if (inputStream.ready()) {
                    message = clientIn.readLine();
                    messenger.distribution(message);
//                    messenger.printMessage(message = clientIn.readLine());
//                    clientOut.write(message + "\n");
//                    clientOut.flush();
//                    messenger.addToHistory(message);
                }
            } catch (IOException e) {
                messenger.printLog(e.getMessage());
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        clientOut.write(message + "\n");
        clientOut.flush();
    }
}
