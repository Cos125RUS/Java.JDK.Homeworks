package org.example.client;

import java.io.*;
import java.net.Socket;

public class Connect extends Thread implements Connection {
    private Client client;
    private Socket socket;
    private String login;
    private String password;
    private String host;
    private int port;
    private String newMessage;


    public Connect(Client client, String host, int port, String login, String password) throws IOException {
        this.client = client;
        this.login = login;
        this.password = password;
        this.host = host;
        this.port = port;
        this.newMessage = "";
    }

    @Override
    public void send(String message) {
        newMessage = message;
    }

    @Override
    public void print(String message) {
        client.printMessage(message);
    }

    @Override
    public void check(boolean answer) {
        client.check(answer);
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            try {
                writer.write(login);
                writer.write(password);
                String answer = reader.readLine();
                print(answer);
                if (answer.equals("access")) {
                    print("Соединение установлено");
                    check(true);
                    while (socket.isConnected()) {
                        if (newMessage.equals("")) {
                            sleep(60);
                        } else {
                            writer.write(newMessage);
                        }
                    }
                } else {
//                    TODO Ошибка авторизации
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reader.close();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
