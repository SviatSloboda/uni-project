package ua.foxminded.backend.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.backend.controller.BaseController;
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;
import ua.foxminded.backend.model.user.Teacher;
import ua.foxminded.backend.repository.user.TeacherRepository;
import ua.foxminded.backend.service.user.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController<Teacher, TeacherService, TeacherRepository, TeacherNotFoundException> {
    public TeacherController(TeacherService teacherService) {
        super(teacherService);
    }

    @Override
    protected String getAttributeName() {
        return "teachers";
    }

    @Override
    protected String getPathOfTemplateOfEntity() {
        return "components/user/teacher.html";
    }
}
