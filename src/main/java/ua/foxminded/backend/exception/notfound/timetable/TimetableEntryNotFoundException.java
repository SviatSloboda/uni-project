package ua.foxminded.backend.exception.notfound.timetable;

import java.util.NoSuchElementException;

public class TimetableEntryNotFoundException extends NoSuchElementException {
    public TimetableEntryNotFoundException(String s) {
        super(s);
    }
}
