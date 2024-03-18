package ua.foxminded.backend.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.foxminded.backend.exception.ErrorResponse;
import ua.foxminded.backend.exception.notfound.course.CourseNotFoundException;
import ua.foxminded.backend.exception.notfound.course.GroupNotFoundException;
import ua.foxminded.backend.exception.notfound.course.LessonNotFoundException;
import ua.foxminded.backend.exception.notfound.course.MarkNotFoundException;
import ua.foxminded.backend.exception.notfound.course.TaskNotFoundException;
import ua.foxminded.backend.exception.notfound.institution.FacultyNotFoundException;
import ua.foxminded.backend.exception.notfound.institution.InstituteNotFoundException;
import ua.foxminded.backend.exception.notfound.timetable.TimetableEntryNotFoundException;
import ua.foxminded.backend.exception.notfound.timetable.TimetableNotFoundException;
import ua.foxminded.backend.exception.notfound.user.StudentNotFoundException;
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class EntityNotFoundExceptionHandler {

    @ExceptionHandler({
            TeacherNotFoundException.class,
            StudentNotFoundException.class,
            FacultyNotFoundException.class,
            InstituteNotFoundException.class,
            TaskNotFoundException.class,
            MarkNotFoundException.class,
            LessonNotFoundException.class,
            GroupNotFoundException.class,
            CourseNotFoundException.class,
            TimetableNotFoundException.class,
            TimetableEntryNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoSuchElementException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
