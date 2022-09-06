package ru.clevertec.check.util.exception;

public class ObjectNotFoundException extends Exception {

    private static final String message = " not found";
    private long id;

    public ObjectNotFoundException(Class<?> object, long id) {
        super(object.getName() + " id: " + id + message);
        this.id = id;
    }


    public ObjectNotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }

    public ObjectNotFoundException(Class<?> object) {
        super(object.getName().concat(message));
    }

    public long getId() {
        return id;
    }
}
