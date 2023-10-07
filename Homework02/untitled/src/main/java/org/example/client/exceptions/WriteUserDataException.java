package org.example.client.exceptions;

import java.io.IOException;

public class WriteUserDataException extends IOException {

    public WriteUserDataException() {
        super("Write user data is fail");
    }

    public WriteUserDataException(String message) {
        super(message);
    }
}
