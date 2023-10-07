package org.example.server.exceptions;

public class StopServerException extends RuntimeException{
    public StopServerException() {
        super("Остановка сервера не выполнена");
    }

    public StopServerException(String message) {
        super(message);
    }
}
