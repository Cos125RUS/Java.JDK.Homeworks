package org.example.client;

public interface ClientView {
    /**
     * Печать сообщения
     * @param message сообщение от сервера
     */
    void printMessage(String message);

    /**
     * Заполнение пользовательских данных
     * @param userData
     */
    void setUserData(String[] userData);

    /**
     * Добавление слушателей отправки сообщений
     */
    void addMessageListener();

    /**
     * Успешная авторизация
     */
    void authorization();
}
