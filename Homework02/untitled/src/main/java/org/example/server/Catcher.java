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
    private ServerSocket serverSocket;
    private final Server server;
    private String message;
    private final ArrayList<User> users;

    /**
     * Отлов новых подключений
     *
     * @param server
     */
    public Catcher(Server server, ServerSocket serverSocket) {
        this.server = server;
        this.message = "";
        this.users = new ArrayList<>();
        this.serverSocket = serverSocket;
    }


    @Override
    public void run() {
        try {
            serverSocket.setSoTimeout(1000);
            while (server.isRun()) {
                try {
                    try {
                        Socket client = serverSocket.accept();
                        try {
                            if (server.isRun()) {
                                InputStreamReader inputStream = new InputStreamReader(
                                        client.getInputStream());
                                BufferedReader clientIn = new BufferedReader(inputStream);
                                BufferedWriter clientOut = new BufferedWriter(new OutputStreamWriter(
                                        client.getOutputStream()));
                                String login = clientIn.readLine();
                                String password = clientIn.readLine();
                                if (server.authorization(login, password)) {
                                    addUser(login, password, client, inputStream, clientIn, clientOut);
                                } else {
                                    clientOut.write("denied\n");
                                    clientOut.flush();
                                }
                            }
                        } catch (IOException | InterruptedException e) {
                            server.printLog(e.getMessage());
                        }
                    } catch (SocketException e) {
                        server.printLog("Соединение на стороне клиента разорвано");
                    }
                } catch (IOException e) {
//                    server.printLog(e.getMessage());
                    //Таймаут соединения
                }
            }
        } catch (SocketException e) {
            server.printLog(e.getMessage());
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
            clientOut.write(line + "\n");
            clientOut.flush();
        }
        clientOut.write("finish\n");
        clientOut.flush();
        server.newUser(login, password, client, inputStream, clientIn, clientOut);
    }

//    @Override
//    public void stopCatching(String host, int port) {
//        try {
//            Socket socket = new Socket(host, port);
//            socket.close();
//        } catch (IOException e) {
//            server.printLog(e.getMessage() + "\n");
//        }
//    }
}
