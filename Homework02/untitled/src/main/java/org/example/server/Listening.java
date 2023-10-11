package org.example.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public interface Listening {
    void listen(InputStreamReader inputStream, BufferedReader clientIn, BufferedWriter clientOut)
            throws IOException;
    void addUser(String login, String password, Socket client, InputStreamReader inputStream,
                 BufferedReader clientIn, BufferedWriter clientOut)
            throws IOException, InterruptedException;
//    void stopCatching(String host, int port);
}
