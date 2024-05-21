package ua.foxminded.backend.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ua.foxminded.backend.controller.BaseControllerTest;
import ua.foxminded.backend.exception.notfound.course.LessonNotFoundException;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.repository.course.LessonRepository;
import ua.foxminded.backend.service.course.LessonService;

@WebMvcTest(LessonController.class)
class LessonControllerTest extends BaseControllerTest<Lesson, LessonService, LessonRepository, LessonController, LessonNotFoundException> {

    @Autowired
    private LessonController controller;

    @Override
    protected LessonController getController() {
        return controller;
    }

    @Override
    protected String getUrlOfPageOfEntity() {
        return "/lesson";
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
