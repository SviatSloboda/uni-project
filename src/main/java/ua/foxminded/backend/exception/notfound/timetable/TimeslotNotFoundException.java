package ua.foxminded.backend.exception.notfound.timetable;

import java.util.NoSuchElementException;

public class TimeslotNotFoundException extends NoSuchElementException {
    public TimeslotNotFoundException(String s) {
        super(s);
    }
}
