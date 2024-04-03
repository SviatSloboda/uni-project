package ua.foxminded.backend.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;
import ua.foxminded.backend.model.course.Group;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.user.Teacher;
import ua.foxminded.backend.model.user.User;
import ua.foxminded.backend.repository.user.TeacherRepository;
import ua.foxminded.backend.service.BaseService;
import ua.foxminded.backend.service.course.TaskService;
import ua.foxminded.backend.service.timetable.TimetableService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TeacherService extends BaseService<Teacher, TeacherRepository, TeacherNotFoundException> {
    private final TaskService taskService;
    private final TimetableService timetableService;

    protected TeacherService(TeacherRepository teacherRepository, TaskService taskService, TimetableService timetableService) {
        super(teacherRepository);
        this.taskService = taskService;
        this.timetableService = timetableService;
    }

    @Transactional
    public Teacher saveTeacher(Teacher teacher, User user) {
        teacher.setFirstName(user.getFirstName());
        teacher.setLastName(user.getLastName());
        teacher.setLogin(user.getLogin());
        teacher.setPassword(user.getPassword());

        return repository.save(teacher);
    }

    @Transactional
    public List<Lesson> viewTimeTableByPeriod(String teacherId, LocalDateTime localDateTime, String period) {
        List<Group> groupsOfTeacher = getGroupsOfTeacher(teacherId);

        List<Lesson> lessonsInDay = new ArrayList<>();

        switch (period) {
            case "day":
                for (Group group : groupsOfTeacher) {
                    lessonsInDay.addAll(timetableService.viewDailyTimetable(group.getTimetable().getId(), localDateTime));
                }
                break;

            case "week":
                for (Group group : groupsOfTeacher) {
                    lessonsInDay.addAll(timetableService.viewWeeklyTimetable(group.getTimetable().getId(), localDateTime));
                }
                break;

            case "month":
                for (Group group : groupsOfTeacher) {
                    lessonsInDay.addAll(timetableService.viewMonthlyTimetable(group.getTimetable().getId(), localDateTime));
                }
                break;

            default:
                throw new IllegalStateException("You have set wrong period!");
        }

        return lessonsInDay.stream()
                .sorted(Comparator.comparing(lesson -> lesson.getTimetableEntries().get(0).getTimeslot().getStartTime()))
                .toList();

    }

    @Transactional
    public Task reviewTask(String taskId, byte markValue, String feedback) {
        return taskService.reviewTask(taskId, markValue, feedback);
    }

    private List<Group> getGroupsOfTeacher(String teacherId) {
        return getById(teacherId).getCourses().stream()
                .flatMap(course -> course.getGroups().stream())
                .toList();
    }

    @Override
    protected TeacherNotFoundException createNotFoundException(String message) {
        return new TeacherNotFoundException(message);
    }
}
