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
    private InputStreamReader inputStream;
    private BufferedReader reader;
    private BufferedWriter writer;


    public Connect(Client client, String host, int port, String login, String password) throws IOException {
        this.client = client;
        this.login = login;
        this.password = password;
        this.host = host;
        this.port = port;
        this.newMessage = "";
    }

    @Override
    public String send() {
        return client.sendMessage();
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
            inputStream = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStream);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            try {
                print("Подключение...\n");
                writer.write(login + "\n");
                writer.flush();
                writer.write(password + "\n");
                writer.flush();
                if (reader.readLine().equals("access")) {
                    check(true);
                    String history = "";
                    while (!(history = reader.readLine()).matches("finish")) {
                        print(history + "\n");
                    }
                    while (socket.isConnected()) {
                        if (inputStream.ready()) {
                            print(reader.readLine() + "\n");
                        } else if (!(newMessage = send()).isEmpty()) {
                            writer.write(newMessage + "\n");
                            writer.flush();
                        } else
                            sleep(60);
                    }
                } else {
                    check(false);
//                    TODO Ошибка авторизации
                }
            } catch (IOException e) {
                print(e.getMessage());
            } catch (InterruptedException e) {
                print(e.getMessage());
            } finally {
                reader.close();
                writer.close();
            }
        } catch (IOException e) {
            print(e.getMessage());
        } finally {
            try {
                socket.close();
                print("Соединение разорвано");
            } catch (IOException e) {
                print(e.getMessage());
            }
        }

    }
}
