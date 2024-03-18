package ua.foxminded.backend.exception.notfound.course;

import java.util.NoSuchElementException;

public class GroupNotFoundException extends NoSuchElementException {
    public GroupNotFoundException(String s) {
        super(s);
    }
}
