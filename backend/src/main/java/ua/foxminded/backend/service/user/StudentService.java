package ua.foxminded.backend.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.notfound.user.StudentNotFoundException;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.model.course.Mark;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.model.user.User;
import ua.foxminded.backend.repository.user.StudentRepository;
import ua.foxminded.backend.service.BaseService;
import ua.foxminded.backend.service.course.MarkService;
import ua.foxminded.backend.service.course.TaskService;
import ua.foxminded.backend.service.timetable.TimetableService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService extends BaseService<Student, StudentRepository, StudentNotFoundException> {
    private final MarkService markService;
    private final TaskService taskService;
    private final TimetableService timetableService;

    protected StudentService(StudentRepository repository, MarkService markService, TaskService taskService, TimetableService timetableService) {
        super(repository);
        this.markService = markService;
        this.taskService = taskService;
        this.timetableService = timetableService;
    }

    @Transactional
    public Student saveStudent(Student student, User user) {
        student.setFirstName(user.getFirstName());
        student.setLastName(user.getLastName());
        student.setLogin(user.getLogin());
        student.setPassword(user.getPassword());

        return repository.save(student);
    }

    @Transactional(readOnly = true)
    public double calculateGPAOfStudent(String studentId) {
        return markService.calculateAverageMark(getById(studentId));
    }

    @Transactional(readOnly = true)
    public List<Mark> getAllMarksOfStudent(String studentId) {
        return getById(studentId).getTasks().stream().map(Task::getMark).toList();
    }

    @Transactional(readOnly = true)
    public List<Mark> getAllMarksOfStudentByCourseId(String studentId, String courseId) {
        return markService.getAllMarksByCourse(getById(studentId), courseId);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasksOfStudent(String studentId) {
        return getById(studentId).getTasks();
    }

    @Transactional
    public Task submitTask(String studentId, String taskId, String answer) {
        Student student = getById(studentId);

        return taskService.submitTask(student, taskId, answer);
    }

    @Transactional(readOnly = true)
    public List<Lesson> viewDailyTimetable(String studentId, LocalDateTime localDateTime) {
        return timetableService.viewDailyTimetable(getById(studentId).getGroup().getTimetable().getId(), localDateTime);
    }

    @Transactional(readOnly = true)
    public List<Lesson> viewWeeklyTimetable(String studentId, LocalDateTime localDateTime) {
        return timetableService.viewWeeklyTimetable(getById(studentId).getGroup().getTimetable().getId(), localDateTime);
    }

    @Transactional(readOnly = true)
    public List<Lesson> viewMonthlyTimetable(String studentId, LocalDateTime localDateTime) {
        return timetableService.viewMonthlyTimetable(getById(studentId).getGroup().getTimetable().getId(), localDateTime);
    }

    @Override
    protected StudentNotFoundException createNotFoundException(String message) {
        return new StudentNotFoundException(message);
    }
}
