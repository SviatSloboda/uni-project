package ua.foxminded.backend.exception.notfound.user;

import java.util.NoSuchElementException;

public class TeacherNotFoundException extends NoSuchElementException {
    public TeacherNotFoundException(String s) {
        super(s);
    }
}
