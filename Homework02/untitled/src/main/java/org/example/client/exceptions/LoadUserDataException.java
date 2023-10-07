package org.example.client.exceptions;

import java.io.IOException;

public class LoadUserDataException extends IOException {

    public LoadUserDataException() {
        super("Opened User Data File is Fail");
    }

    public LoadUserDataException(String message) {
        super(message);
    }
}
