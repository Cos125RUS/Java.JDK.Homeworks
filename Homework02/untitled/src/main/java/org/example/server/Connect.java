package org.example.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class Connect implements Connection {
    public Socket socket;
    public String name;
    private static BufferedReader reader;
    private static BufferedReader serverIn;
    private static BufferedWriter serverOut;

    public Connect(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void send() {

    }
}
