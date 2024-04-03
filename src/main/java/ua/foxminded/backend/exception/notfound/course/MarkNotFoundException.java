package ua.foxminded.backend.exception.notfound.course;

import java.util.NoSuchElementException;

public class MarkNotFoundException extends NoSuchElementException {
    public MarkNotFoundException(String s) {
        super(s);
    }
}
