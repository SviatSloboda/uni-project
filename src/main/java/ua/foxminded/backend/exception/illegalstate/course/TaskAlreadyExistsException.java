package ua.foxminded.backend.exception.illegalstate.course;

public class TaskAlreadyExistsException extends IllegalStateException {
    public TaskAlreadyExistsException(String s) {
        super(s);
    }
}
