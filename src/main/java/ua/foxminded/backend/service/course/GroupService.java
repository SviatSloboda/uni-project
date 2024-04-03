package ua.foxminded.backend.service.course;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.illegalstate.course.LessonAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.user.StudentAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.course.GroupNotFoundException;
import ua.foxminded.backend.exception.notfound.course.LessonNotFoundException;
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;
import ua.foxminded.backend.model.course.Group;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.repository.course.GroupRepository;
import ua.foxminded.backend.service.BaseService;

@Service
public class GroupService extends BaseService<Group, GroupRepository, GroupNotFoundException> {
    protected GroupService(GroupRepository repository) {
        super(repository);
    }

    @Override
    protected GroupNotFoundException createNotFoundException(String message) {
        return new GroupNotFoundException(message);
    }

    @Transactional
    public Group addStudentToGroup(String groupId, Student student) {
        Group group = getById(groupId);

        if (studentExistsInGroup(group, student.getId())){
            throw new StudentAlreadyExistsException("Student with id: " + student.getId() + " already exists in Group with id: " + groupId);
        }

        group.getStudents().add(student);

        return repository.save(group);
    }

    @Transactional
    public Group removeStudentFromGroupById(String groupId, String studentId) {
        Group group = getById(groupId);

        Student studentToRemove = group.getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .findAny()
                .orElseThrow(() -> new TeacherNotFoundException("There is no such Student with id: " + studentId));

        group.getStudents().remove(studentToRemove);

        return repository.save(group);
    }

    @Transactional
    public Group addLessonToGroup(String groupId, Lesson lesson) {
        Group group = getById(groupId);

        if (lessonExistsInGroup(group, lesson.getId())) {
            throw new LessonAlreadyExistsException("Lesson with id: " + lesson.getId() + " already exists in Group with id: " + groupId);
        }

        group.getLessons().add(lesson);

        return repository.save(group);
    }

    @Transactional
    public Group removeLessonFromGroupById(String groupId, String lessonId) {
        Group group = getById(groupId);

        Lesson lessonToRemove = group.getLessons().stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findAny()
                .orElseThrow(() -> new LessonNotFoundException("There is no such Lesson with id: " + lessonId));

        group.getLessons().remove(lessonToRemove);

        return repository.save(group);
    }

    private boolean studentExistsInGroup(Group group, String studentId) {
        return group.getStudents().stream()
                .anyMatch(student -> student.getId().equals(studentId));
    }

    private boolean lessonExistsInGroup(Group group, String lessonId) {
        return group.getLessons().stream()
                .anyMatch(lesson -> lesson.getId().equals(lessonId));
    }
}
