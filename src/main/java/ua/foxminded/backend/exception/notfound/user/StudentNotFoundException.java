package ua.foxminded.backend.exception.notfound.user;

import java.util.NoSuchElementException;

public class StudentNotFoundException extends NoSuchElementException {
    public StudentNotFoundException(String s) {
        super(s);
    }
}
