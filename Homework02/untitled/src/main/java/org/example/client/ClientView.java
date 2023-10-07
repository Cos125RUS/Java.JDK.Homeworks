package org.example.client;

public interface ClientView {
    /**
     * Печать сообщения
     * @param message сообщение от сервера
     */
    void printMessage(String message);
    void setUserData(String[] userData);
}
