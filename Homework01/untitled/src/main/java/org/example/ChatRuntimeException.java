package org.example;

class ChatException extends RuntimeException {

    public ChatException() {
        super("ChatRuntimeException");
    }

    public ChatException(String message) {
        super(message);
    }
}

class ConnectionException extends ChatException {

    public ConnectionException() {
        super("Connection Lost");
    }

    public ConnectionException(String message) {
        super(message);
    }
}

class StopServerException extends ChatException {

    public StopServerException() {
        super("Can't stopped server now");
    }

    public StopServerException(String message) {
        super(message);
    }
}

class StartServerException extends ChatException {

    public StartServerException() {
        super("Start server is failed");
    }

    public StartServerException(String message) {
        super(message);
    }
}