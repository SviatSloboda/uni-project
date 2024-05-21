package ua.foxminded.backend.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ua.foxminded.backend.controller.BaseControllerTest;
import ua.foxminded.backend.exception.notfound.user.StudentNotFoundException;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.repository.user.StudentRepository;
import ua.foxminded.backend.service.user.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest extends BaseControllerTest<Student, StudentService, StudentRepository, StudentController, StudentNotFoundException> {

    @Autowired
    private StudentController controller;

    @Override
    protected StudentController getController() {
        return controller;
    }

    @Override
    protected String getUrlOfPageOfEntity() {
        return "/student";
    }

    @Override
    protected String getAttributeName() {
        return "students";
    }

    @Override
    protected String getPathOfTemplateOfEntity() {
        return "components/user/student.html";
    }
}
