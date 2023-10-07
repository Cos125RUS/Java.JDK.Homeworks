package org.example.client;

public interface Connection {
    String send();
    void print(String message);

    void check(boolean answer);
}
