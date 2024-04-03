package ua.foxminded.backend.exception.notfound.institution;

import java.util.NoSuchElementException;

public class InstituteNotFoundException extends NoSuchElementException {
    public InstituteNotFoundException(String s) {
        super(s);
    }
}
