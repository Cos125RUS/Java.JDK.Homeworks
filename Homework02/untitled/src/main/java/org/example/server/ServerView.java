package org.example.server;

public interface ServerView {
    /**
     * Вывод логов на экран
     * @param log системное сообщение
     */
    void printLog(String log);
    /**
     * Вывод сообщений пользователей на экран
     * @param message сообщение
     */
    void printMessage(String message);

    /**
     * Инициировать процедуру запуска сервера
     */
    void startServer();

    /**
     * Инициировать процедуру остановки сервера
     */
    void stopServer();
}
