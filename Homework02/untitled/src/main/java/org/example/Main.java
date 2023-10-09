package org.example;

import org.example.client.Client;
import org.example.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new Server();
        new Client();
        new Client();
        new Client();
    }
}
