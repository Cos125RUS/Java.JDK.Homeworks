package org.example.server;

public interface Distribute {
    void addMember(User user);
    void checkNewMember();
    void printMessage(String message);
    void addToHistory(String message);
    void printLog(String log);
    void distribution(String message);
}
