package ua.foxminded.backend.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ua.foxminded.backend.controller.BaseControllerTest;
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;
import ua.foxminded.backend.model.user.Teacher;
import ua.foxminded.backend.repository.user.TeacherRepository;
import ua.foxminded.backend.service.user.TeacherService;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest extends BaseControllerTest<Teacher, TeacherService, TeacherRepository, TeacherController, TeacherNotFoundException> {

    @Autowired
    private TeacherController controller;

    @Override
    protected TeacherController getController() {
        return controller;
    }

    @Override
    protected String getUrlOfPageOfEntity() {
        return "/teacher";
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
