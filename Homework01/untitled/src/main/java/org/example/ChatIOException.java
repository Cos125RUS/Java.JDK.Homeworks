package org.example;

import java.io.IOException;

public class ChatIOException extends IOException {
    public ChatIOException() {
        super("IO Error");
    }

    public ChatIOException(String message) {
        super(message);
    }
}

class LoadUserDataException extends ChatIOException{

    public LoadUserDataException() {
        super("Opened User Data File is Fail");
    }

    public LoadUserDataException(String message) {
        super(message);
    }
}

class AccessErrorException extends ChatIOException {

    public AccessErrorException() {
        super("Access Error");
    }

    public AccessErrorException(String message) {
        super(message);
    }
}

class WriteUserDataException extends ChatIOException {

    public WriteUserDataException() {
        super("Write user data is fail");
    }

    public WriteUserDataException(String message) {
        super(message);
    }
}