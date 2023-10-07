package org.example.server.exceptions;

public class StartServerException extends RuntimeException{
    public StartServerException() {
        super("Невозможно выполнить запуск сервера");
    }

    public StartServerException(String message) {
        super(message);
    }
}
