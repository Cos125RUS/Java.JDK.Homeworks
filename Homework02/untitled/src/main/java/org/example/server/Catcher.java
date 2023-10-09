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
    private InputStreamReader inputStream;

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
                            inputStream = new InputStreamReader(client.getInputStream());
                            clientIn = new BufferedReader(inputStream);
                            clientOut = new BufferedWriter(new OutputStreamWriter(
                                    client.getOutputStream()));
                            String login = clientIn.readLine();
                            String password = clientIn.readLine();
                            if (server.authorization(login, password)) {
                                clientOut.write("access\n");
                                clientOut.flush();
                                for (String line : server.getHistory()) {
                                    sleep(100);
                                    server.printMessage(line);
                                    clientOut.write(line + "\n");
                                    clientOut.flush();
                                }
                                clientOut.write("finish\n");
                                clientOut.flush();
                                server.newUser(login, client);
                                server.printLog("Новый юзер: " + login);
                                while (client.isConnected()) {
                                    if (inputStream.ready()) {
                                        server.printMessage(message = clientIn.readLine());
                                        clientOut.write(message + "\n");
                                        clientOut.flush();
                                        server.addToHistory(message);
                                    }
                                }
                            } else {
                                clientOut.write("denied\n");
                                clientOut.flush();
                            }
                        } catch (IOException | InterruptedException e) {
                            server.printLog(e.getMessage());
                        } finally {
                            clientIn.close();
                            clientOut.close();
                        }
                    } finally {
                        client.close();
                        server.printLog("Соединение на стороне клиента разорвано");
                    }
                } catch (IOException e) {
                    server.printLog(e.getMessage());
                } finally {
                    try {
                        serverSocket.close();
                        server.printLog("Сервер разорвал соединение");
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
