package ua.foxminded.backend.exception.illegalstate.course;

public class CourseAlreadyExistsException extends IllegalStateException {
    public CourseAlreadyExistsException(String s) {
        super(s);
    }
}
