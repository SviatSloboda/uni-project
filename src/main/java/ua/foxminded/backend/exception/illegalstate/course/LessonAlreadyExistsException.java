package ua.foxminded.backend.exception.illegalstate.course;

public class LessonAlreadyExistsException extends IllegalStateException {
    public LessonAlreadyExistsException(String s) {
        super(s);
    }
}
