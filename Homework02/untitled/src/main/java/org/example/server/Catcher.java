package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Выжидатель новых подключений
 */
public class Catcher extends Thread{
    private ServerSocket serverSocket;
    private Server server;

    /**
     * Отлов новых подключений
     * @param serverSocket
     * @param server
     */
    public Catcher(ServerSocket serverSocket, Server server) {
        try {
            this.serverSocket = new ServerSocket(8888);
            this.server = server;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void run() {
        boolean caught = false;
        while (!caught){
            try {
                caught = true;
                Socket client = serverSocket.accept();
                try {
                    System.out.println(serverSocket.isClosed());
                    String login = "login";
                    server.newUser(login, client);
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                client.getInputStream()));
                        while (true) {
                            String word = in.readLine();
                            System.out.println(word);
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
//                        throw new RuntimeException(e);
                    }
                } finally {
                    client.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
//                throw new RuntimeException(e);
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
//                    throw new RuntimeException(e);
                }
            }
        }
    }
}
