package org.example.server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * База Данных
 */
public class Repo implements DataBase{
    private static final String CHAT_HISTORY_PATH = "src/files/server/ChatHistory.txt";
    private static final String CLIENT_LIST_PATH = "src/files/server/ClientList.txt";

    /**
     * История чата
     */
    private ArrayList<String> history;
    /**
     * Список пользователей
     */
    private HashMap<String, String> users;

    public Repo() {
        this.history = new ArrayList<>();
        this.users = new HashMap<>();
    }

    /**
     * Загрузка истории из базы данных
     */
    public void load() throws RuntimeException {
        //Загрузка списка юзеров
        try (BufferedReader br = new BufferedReader(new FileReader(CLIENT_LIST_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split("/");
                users.put(user[0], user[1]);
            }
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Paths.get(CLIENT_LIST_PATH));
            } catch (IOException ex) {
                throw new RuntimeException("Ошибка создания файла списка пользователей");            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл списка пользователей");
        }
        //Загрузку чатов
        try (BufferedReader br = new BufferedReader(new FileReader(CHAT_HISTORY_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                history.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл с историей переписок не найден");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла");
        }
    }


    @Override
    public HashMap<String, String> getUsers() {
        return users;
    }

    @Override
    public ArrayList<String> getHistory() {
        return history;
    }

    @Override
    public void addMessage(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CHAT_HISTORY_PATH, true))) {
            bw.append(String.format(message + '\n'));
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи истории чата");
        }
    }

    @Override
    public void addUser(String login, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLIENT_LIST_PATH, true))) {
            bw.append(String.format(login + "/" + password + "\n"));
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Paths.get(CLIENT_LIST_PATH));
            } catch (IOException ex) {
                throw new RuntimeException("Ошибка создания файла списка пользователей");            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть на запись файл списка пользователей");
        }
    }
}
