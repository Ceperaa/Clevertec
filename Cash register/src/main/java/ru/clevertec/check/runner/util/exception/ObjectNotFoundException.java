package ru.clevertec.check.runner.util.exception;

public class ObjectNotFoundException extends Exception {

    private static String message = " not found";
    private long id;

    public ObjectNotFoundException(Class<?> object, long id) {
        super(object.getName() + "id: " + id + message);
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
