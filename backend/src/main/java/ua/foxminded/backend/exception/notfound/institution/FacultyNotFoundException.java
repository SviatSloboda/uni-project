package ua.foxminded.backend.exception.notfound.institution;

import java.util.NoSuchElementException;

public class FacultyNotFoundException extends NoSuchElementException {
    public FacultyNotFoundException(String s) {
        super(s);
    }
}
