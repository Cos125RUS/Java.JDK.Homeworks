package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Выжидатель новых подключений
 */
public class Catcher extends Thread {
    private static BufferedReader clientIn;
    private static BufferedWriter clientOut;
    private final int portNumber;
    private ServerSocket serverSocket;
    private Server server;
    private String message;

    /**
     * Отлов новых подключений
     *
     * @param server
     */
    public Catcher(Server server, int portNumber) {
        this.server = server;
        this.portNumber = portNumber;
        this.message = "";
    }


    @Override
    public void run() {
        boolean caught = false;
        try {
            this.serverSocket = new ServerSocket(portNumber);
            while (!caught) {
                try {
                    Socket client = serverSocket.accept();
                    caught = true;
                    try {
                        try {
                            clientIn = new BufferedReader(new InputStreamReader(
                                    client.getInputStream()));
                            clientOut = new BufferedWriter(new OutputStreamWriter(
                                    client.getOutputStream()));
                            String login = clientIn.readLine();
                            server.printLog(login);
//                            String password = clientIn.readLine();
//                            server.printLog(password);
                            clientOut.write("access\n");
                            clientOut.flush();
//                          TODO Добавить авторизацию
                            server.newUser(login, client);
                            server.printLog("Новый юзер: " + login);
                            while (client.isConnected()) {
//                                server.printLog("Готов принимать сообщение");
                                server.printMessage(message = clientIn.readLine());
                                clientOut.write(message + "\n");
                                clientOut.flush();
                            }
                        } catch (IOException e) {
                            server.printLog(e.getMessage());
                        } finally {
                            clientIn.close();
                            clientOut.close();
                        }
                    } finally {
                        client.close();
                        server.printLog("Соединение разорвано");
                    }
                } catch (IOException e) {
                    server.printLog(e.getMessage());
                } finally {
                    try {
                        serverSocket.close();
                        server.printLog("Сервер закрыл соединение");
                    } catch (IOException e) {
                        server.printLog(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            server.printLog(e.getMessage());
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                server.printLog(e.getMessage());
            }
        }
    }
}
