package ua.foxminded.backend.service.course;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.notfound.course.MarkNotFoundException;
import ua.foxminded.backend.model.course.Mark;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.repository.course.MarkRepository;
import ua.foxminded.backend.service.BaseService;

import java.util.List;

@Service
public class MarkService extends BaseService<Mark, MarkRepository, MarkNotFoundException> {

    protected MarkService(MarkRepository repository) {
        super(repository);
    }

    @Transactional(readOnly = true)
    public double calculateAverageMark(Student student) {
        return student.getTasks().stream().mapToInt(task -> task.getMark().getMarkValue())
                .average()
                .orElse(0.0);
    }

    @Transactional
    public List<Mark> getAllMarksByCourse(Student student, String courseId) {
        return student.getTasks().stream()
                .map(Task::getMark).
                filter(mark -> mark.getTask().getCourse().getId().equals(courseId)).toList();
    }

    @Override
    protected MarkNotFoundException createNotFoundException(String message) {
        return new MarkNotFoundException(message);
    }
}
