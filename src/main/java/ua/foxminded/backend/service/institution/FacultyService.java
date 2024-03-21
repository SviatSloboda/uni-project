package ua.foxminded.backend.service.institution;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.illegalstate.course.CourseAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.institution.FacultyNotFoundException;
import ua.foxminded.backend.model.course.Course;
import ua.foxminded.backend.model.institution.Faculty;
import ua.foxminded.backend.repository.institution.FacultyRepository;
import ua.foxminded.backend.service.BaseService;

@Service
public class FacultyService extends BaseService<Faculty, FacultyRepository, FacultyNotFoundException> {

    protected FacultyService(FacultyRepository facultyRepository) {
        super(facultyRepository);
    }

    @Transactional
    public Faculty addCourse(String facultyId, Course course) {
        Faculty faculty = getById(facultyId);

        if (courseExistsInFaculty(faculty, course.getId())){
            throw new CourseAlreadyExistsException("Course with id: " + course.getId() + " already exists in Faculty with id: " + facultyId);
        }

        faculty.getCourses().add(course);

        return repository.save(faculty);
    }

    @Transactional
    public Faculty removeCourseById(String facultyId, String courseId) {
        Faculty faculty = getById(facultyId);

        Course courseToRemove = faculty.getCourses().stream()
                .filter(course -> course.getId().equals(courseId))
                .findAny()
                .orElseThrow(() -> new FacultyNotFoundException("There is no such Course with id: " + courseId +
                                                                " in this Institution with id: " + facultyId));

        faculty.getCourses().remove(courseToRemove);

        return repository.save(faculty);
    }

    private boolean courseExistsInFaculty(Faculty faculty, String courseId) {
        return faculty.getCourses().stream()
                .anyMatch(course -> course.getId().equals(courseId));
    }

    @Override
    protected FacultyNotFoundException createNotFoundException(String message) {
        return new FacultyNotFoundException(message);
    }
}
