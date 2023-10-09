package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Выжидатель новых подключений
 */
public class Catcher extends Thread implements Listening{
//    private static BufferedReader clientIn;
//    private static BufferedWriter clientOut;
//    private final int portNumber;
    private ServerSocket serverSocket;
    private final Server server;
    private String message;
//    private InputStreamReader inputStream;
    private final ArrayList<User> users;

    /**
     * Отлов новых подключений
     *
     * @param server
     */
    public Catcher(Server server, ServerSocket serverSocket) {
        this.server = server;
//        this.portNumber = portNumber;
        this.message = "";
        this.users = new ArrayList<>();
        this.serverSocket = serverSocket;
    }


    @Override
    public void run() {
//        boolean caught = false;
        try {
//            this.serverSocket = new ServerSocket(portNumber);
            while (server.isRun()) {
                try {
                    try {
                        Socket client = serverSocket.accept();
//                        caught = true;
                        try {
                            InputStreamReader inputStream = new InputStreamReader(
                                    client.getInputStream());
                            BufferedReader clientIn = new BufferedReader(inputStream);
                            BufferedWriter clientOut = new BufferedWriter(new OutputStreamWriter(
                                    client.getOutputStream()));
                            String login = clientIn.readLine();
//                            server.printLog(login);
                            String password = clientIn.readLine();
//                            server.printLog(password);
                            if (server.authorization(login, password)) {
                                addUser(login, password, client, inputStream, clientIn, clientOut);
//                                while (client.isConnected()) {
//                                    listen(inputStream, clientIn, clientOut);
//                                }
                            } else {
                                clientOut.write("denied\n");
                                clientOut.flush();
                            }
                        } catch (IOException | InterruptedException e) {
                            server.printLog(e.getMessage());
                        }
                    } catch (SocketException e) {
                        server.printLog("Соединение на стороне клиента разорвано");
                    }
                } catch (IOException e) {
                    server.printLog(e.getMessage());
//                } finally {
//                    try {
//                        serverSocket.close();
//                        server.printLog("Сервер разорвал соединение");
//                    } catch (IOException e) {
//                        server.printLog(e.getMessage());
//                    }
                }
            }
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                server.printLog(e.getMessage());
            }
        }
    }

    @Override
    public void listen(InputStreamReader inputStream, BufferedReader clientIn,
                       BufferedWriter clientOut) throws IOException {
        if (inputStream.ready()) {
            server.printMessage(message = clientIn.readLine());
            clientOut.write(message + "\n");
            clientOut.flush();
            server.addToHistory(message);
        }
    }

    /**
     * Добавление нового пользователя
     * @param login
     * @param password
     * @param client
     * @param inputStream
     * @param clientIn
     * @param clientOut
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void addUser(String login, String password, Socket client,
                        InputStreamReader inputStream, BufferedReader clientIn,
                        BufferedWriter clientOut) throws IOException, InterruptedException {
        clientOut.write("access\n");
        clientOut.flush();
        for (String line : server.getHistory()) {
            sleep(100);
//            server.printMessage(line);
            clientOut.write(line + "\n");
            clientOut.flush();
        }
        clientOut.write("finish\n");
        clientOut.flush();
        server.newUser(login, password, client, inputStream, clientIn, clientOut);
//        users.add(new User(login, password, client, inputStream, clientIn, clientOut));
    }
}
