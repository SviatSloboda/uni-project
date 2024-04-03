package ua.foxminded.backend.exception.illegalstate.user;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String s) {
        super(s);
    }
}
