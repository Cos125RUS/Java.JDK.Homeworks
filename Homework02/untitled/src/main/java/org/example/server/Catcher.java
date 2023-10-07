package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Выжидатель новых подключений
 */
public class Catcher extends Thread {
    private final int portNumber;
    private ServerSocket serverSocket;
    private Server server;

    /**
     * Отлов новых подключений
     *
     * @param server
     */
    public Catcher(Server server, int portNumber) {
        this.server = server;
        this.portNumber = portNumber;
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
                            BufferedReader clientIn = new BufferedReader(new InputStreamReader(
                                    client.getInputStream()));
                            BufferedWriter clientOut = new BufferedWriter(new OutputStreamWriter(
                                    client.getOutputStream()));
                            String login = clientIn.readLine();
                            server.printLog(login);
                            String password = clientIn.readLine();
                            server.printLog(password);
                            clientOut.write("access");
//                          TODO Добавить авторизацию
                            server.newUser(login, client);
                            while (client.isConnected()) {
                                server.printMessage(clientIn.readLine());
                            }
                        } catch (IOException e) {
                            server.printLog(e.getMessage());
                        }
                    } finally {
                        client.close();
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
