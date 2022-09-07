package ru.clevertec.check.runner.util.exception;

public class ObjectNotFoundException extends Exception {

    private static final String message = " not found";
    private final long id;

    public ObjectNotFoundException(Class<?> object, long id) {
        super(object.getName() + " id: " + id + message);
        this.id = id;
    }


    public ObjectNotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
