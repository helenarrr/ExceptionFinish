package org.example.exception;

public class FullNameExceptionException extends Exception {
    private final String data;

    public String getData() {
        return data;
    }

    public FullNameExceptionException(String userData, String message) {
        super(message);
        data = userData;
    }


}
