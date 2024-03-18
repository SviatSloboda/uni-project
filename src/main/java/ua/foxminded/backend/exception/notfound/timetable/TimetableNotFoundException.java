package ua.foxminded.backend.exception.notfound.timetable;

import java.util.NoSuchElementException;

public class TimetableNotFoundException extends NoSuchElementException {
    public TimetableNotFoundException(String s) {
        super(s);
    }
}
