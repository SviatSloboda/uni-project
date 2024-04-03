package ua.foxminded.backend.exception.notfound.course;

import java.util.NoSuchElementException;

public class TaskNotFoundException extends NoSuchElementException {
    public TaskNotFoundException(String s) {
        super(s);
    }
}
