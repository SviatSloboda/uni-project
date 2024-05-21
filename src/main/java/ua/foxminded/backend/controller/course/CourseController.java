package ua.foxminded.backend.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.backend.controller.BaseController;
import ua.foxminded.backend.exception.notfound.course.CourseNotFoundException;
import ua.foxminded.backend.model.course.Course;
import ua.foxminded.backend.repository.course.CourseRepository;
import ua.foxminded.backend.service.course.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController extends BaseController<Course, CourseService, CourseRepository, CourseNotFoundException> {
    public CourseController(CourseService courseService) {
        super(courseService);
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
