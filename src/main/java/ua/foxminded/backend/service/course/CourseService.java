package ua.foxminded.backend.service.course;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.illegalstate.course.GroupAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.course.TaskAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.user.TeacherAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.course.CourseNotFoundException;
import ua.foxminded.backend.exception.notfound.course.GroupNotFoundException;
import ua.foxminded.backend.exception.notfound.course.TaskNotFoundException;
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;
import ua.foxminded.backend.model.course.Course;
import ua.foxminded.backend.model.course.Group;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.user.Teacher;
import ua.foxminded.backend.repository.course.CourseRepository;
import ua.foxminded.backend.service.BaseService;

@Service
public class CourseService extends BaseService<Course, CourseRepository, CourseNotFoundException> {
    private static final String EXCEPTION_MESSAGE = " already exists in Course with id: ";

    protected CourseService(CourseRepository repository) {
        super(repository);
    }

    @Transactional
    public Course addTeacherToCourse(String courseId, Teacher teacher) {
        Course course = getById(courseId);

        if (teacherExistsInCourse(course, teacher.getId()))
            throw new TeacherAlreadyExistsException("Teacher with id: " + course.getId() + EXCEPTION_MESSAGE + courseId);

        course.getTeachers().add(teacher);

        return repository.save(course);
    }

    @Transactional
    public Course removeTeacherFromCourseById(String courseId, String teacherId) {
        Course course = getById(courseId);

        Teacher teacherToRemove = course.getTeachers().stream()
                .filter(teacher -> teacher.getId().equals(teacherId))
                .findAny()
                .orElseThrow(() -> new TeacherNotFoundException("There is no such Teacher with id: " + teacherId));

        course.getTeachers().remove(teacherToRemove);

        return repository.save(course);
    }

    @Transactional
    public Course addGroupToCourse(String courseId, Group group) {
        Course course = getById(courseId);

        if (groupExistsInCourse(course, group.getId()))
            throw new GroupAlreadyExistsException("Group with id: " + group.getId() + EXCEPTION_MESSAGE + courseId);

        course.getGroups().add(group);

        return repository.save(course);
    }

    @Transactional
    public Course removeGroupFromCourse(String courseId, String groupId) {
        Course course = getById(courseId);

        Group groupToRemove = course.getGroups().stream()
                .filter(group -> group.getId().equals(groupId))
                .findAny()
                .orElseThrow(() -> new GroupNotFoundException("There is no such Group with id: " + groupId));

        course.getGroups().remove(groupToRemove);

        return repository.save(course);
    }

    @Transactional
    public Course addTaskToCourse(String courseId, Task task) {
        Course course = getById(courseId);

        if (taskExistsInCourse(course, task.getId())) {
            throw new TaskAlreadyExistsException("Task with id: " + task.getId() + EXCEPTION_MESSAGE + courseId);
        }
        course.getTasks().add(task);

        return repository.save(course);
    }

    @Transactional
    public Course removeTaskFromCourseById(String courseId, String taskId) {
        Course course = getById(courseId);

        Task taskToRemove = course.getTasks().stream()
                .filter(task -> task.getId().equals(taskId))
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException("There is no such Task with id:" + taskId));

        course.getTasks().remove(taskToRemove);

        return repository.save(course);
    }

    private boolean teacherExistsInCourse(Course course, String teacherId) {
        return course.getTeachers().stream()
                .anyMatch(teacher -> teacher.getId().equals(teacherId));
    }

    private boolean groupExistsInCourse(Course course, String groupId) {
        return course.getGroups().stream()
                .anyMatch(group -> group.getId().equals(groupId));
    }

    private boolean taskExistsInCourse(Course course, String taskId) {
        return course.getTasks().stream()
                .anyMatch(task -> task.getId().equals(taskId));
    }

    @Override
    protected CourseNotFoundException createNotFoundException(String message) {
        return new CourseNotFoundException(message);
    }
}
