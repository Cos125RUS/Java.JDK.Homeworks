package org.example.server;

import java.io.*;
import java.util.ArrayList;

/**
 * База Данных
 */
public class Repo implements DataBase{
    private static final String CHAT_HISTORY_PATH = "src/files/server/ChatHistory.txt";
//    private static final String CLIENT_LIST_PATH = "src/files/server/ClientList.txt";

    /**
     * История чата
     */
    private String history;

    public Repo() {
        this.history = "";
    }

    /**
     * Загрузка истории из базы данных
     */
    private void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(CHAT_HISTORY_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                history += line + '\n';
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл с историей переписок не найден");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла");
        }
    }


    @Override
    public String getHistory() throws RuntimeException {
        load();
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
}
