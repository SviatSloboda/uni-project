package ua.foxminded.backend.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.backend.controller.BaseController;
import ua.foxminded.backend.exception.notfound.course.LessonNotFoundException;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.repository.course.LessonRepository;
import ua.foxminded.backend.service.course.LessonService;

@Controller
@RequestMapping("/lesson")
public class LessonController extends BaseController<Lesson, LessonService, LessonRepository, LessonNotFoundException> {
    public LessonController(LessonService lessonService) {
        super(lessonService);
    }

    @Override
    protected String getAttributeName() {
        return "lessons";
    }

    @Override
    protected String getPathOfTemplateOfEntity() {
        return "components/course/lesson.html";
    }
}
