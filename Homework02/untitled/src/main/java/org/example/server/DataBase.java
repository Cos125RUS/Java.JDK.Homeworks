package org.example.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface DataBase {
    /**
     * Отправить список пользователей
     * @return
     */
    HashMap<String, String> getUsers();

    /**
     * Отправить историю чата
     * @return
     * @throws IOException
     */
    ArrayList<String> getHistory();

    /**
     * Загрузка Базы Данных
     * @throws RuntimeException
     */
    void load() throws RuntimeException ;

    /**
     * Запись нового сообщения в историю чата
     * @param message
     */
    void addMessage(String message);
    void addUser(String login, String password);
}
