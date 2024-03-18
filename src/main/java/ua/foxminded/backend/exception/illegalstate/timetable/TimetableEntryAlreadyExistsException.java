package ua.foxminded.backend.exception.illegalstate.timetable;

public class TimetableEntryAlreadyExistsException extends IllegalStateException {
    public TimetableEntryAlreadyExistsException(String s) {
        super(s);
    }
}
