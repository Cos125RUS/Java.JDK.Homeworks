package org.example;

import org.example.client.Client;
import org.example.server.Server;

public class Main {
    public static void main(String[] args) {
        new Server();
        new Client();
        new Client();
        new Client();
    }
}
