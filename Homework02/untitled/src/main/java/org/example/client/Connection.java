package org.example.client;

public interface Connection {
    void send(String message);
    void print(String message);

    void check(boolean answer);
}
