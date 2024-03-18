package ua.foxminded.backend.exception.notfound.course;

import java.util.NoSuchElementException;

public class LessonNotFoundException extends NoSuchElementException {
    public LessonNotFoundException(String s) {
        super(s);
    }
}
