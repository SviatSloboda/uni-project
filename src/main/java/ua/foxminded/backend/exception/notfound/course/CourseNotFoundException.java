package ua.foxminded.backend.exception.notfound.course;

import java.util.NoSuchElementException;

public class CourseNotFoundException extends NoSuchElementException {
    public CourseNotFoundException(String s) {
        super(s);
    }
}
