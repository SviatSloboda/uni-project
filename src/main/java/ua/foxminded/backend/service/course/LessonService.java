package ua.foxminded.backend.service.course;

import org.springframework.stereotype.Service;
import ua.foxminded.backend.exception.notfound.course.LessonNotFoundException;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.repository.course.LessonRepository;
import ua.foxminded.backend.service.BaseService;

@Service
public class LessonService extends BaseService<Lesson, LessonRepository, LessonNotFoundException> {

    protected LessonService(LessonRepository lessonRepository) {
        super(lessonRepository);
    }

    @Override
    protected LessonNotFoundException createNotFoundException(String message) {
        return new LessonNotFoundException(message);
    }
}
