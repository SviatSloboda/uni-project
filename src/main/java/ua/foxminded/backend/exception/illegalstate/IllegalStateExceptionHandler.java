package ua.foxminded.backend.exception.illegalstate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.foxminded.backend.exception.ErrorResponse;
import ua.foxminded.backend.exception.illegalstate.course.CourseAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.course.GroupAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.course.LessonAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.course.TaskAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.institution.FacultyAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.timetable.TimetableEntryAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.user.StudentAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.user.TeacherAlreadyExistsException;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class IllegalStateExceptionHandler {

    @ExceptionHandler({
            FacultyAlreadyExistsException.class,
            CourseAlreadyExistsException.class,
            StudentAlreadyExistsException.class,
            TeacherAlreadyExistsException.class,
            GroupAlreadyExistsException.class,
            LessonAlreadyExistsException.class,
            TaskAlreadyExistsException.class,
            TimetableEntryAlreadyExistsException.class,
    })
    public ResponseEntity<Object> createResponseEntity(IllegalStateException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
