package dev.jstec.demo.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException ( String message) {
        super(message);
    }
}
