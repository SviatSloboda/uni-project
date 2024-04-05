package ua.foxminded.backend.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ua.foxminded.backend.controller.BaseControllerTest;
import ua.foxminded.backend.exception.notfound.course.CourseNotFoundException;
import ua.foxminded.backend.model.course.Course;
import ua.foxminded.backend.repository.course.CourseRepository;
import ua.foxminded.backend.service.course.CourseService;

@WebMvcTest(CourseController.class)
class CourseControllerTest extends BaseControllerTest<Course, CourseService, CourseRepository, CourseController, CourseNotFoundException> {

    @Autowired
    private CourseController controller;

    @Override
    protected CourseController getController() {
        return controller;
    }

    @Override
    protected String getUrlOfPageOfEntity() {
        return "/course";
    }

    @Override
    protected String getAttributeName() {
        return "courses";
    }

    @Override
    protected String getPathOfTemplateOfEntity() {
        return "components/course/course.html";
    }
}
