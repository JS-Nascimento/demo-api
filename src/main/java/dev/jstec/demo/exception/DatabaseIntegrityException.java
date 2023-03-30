package dev.jstec.demo.exception;

public class DatabaseIntegrityException extends RuntimeException {
    public DatabaseIntegrityException ( String message) {
        super(message);
    }
}
