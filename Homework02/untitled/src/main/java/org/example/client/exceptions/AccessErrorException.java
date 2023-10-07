package org.example.client.exceptions;

import java.io.IOException;

public class AccessErrorException extends IOException {

    public AccessErrorException() {
        super("Access Error");
    }

    public AccessErrorException(String message) {
        super(message);
    }
}