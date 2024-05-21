package ua.foxminded.backend.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.backend.controller.BaseController;
import ua.foxminded.backend.exception.notfound.user.StudentNotFoundException;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.repository.user.StudentRepository;
import ua.foxminded.backend.service.user.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController<Student, StudentService, StudentRepository, StudentNotFoundException> {
    public StudentController(StudentService studentService) {
        super(studentService);
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
