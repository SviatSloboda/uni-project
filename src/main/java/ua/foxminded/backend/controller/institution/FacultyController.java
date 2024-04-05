package ua.foxminded.backend.controller.institution;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.backend.controller.BaseController;
import ua.foxminded.backend.exception.notfound.institution.FacultyNotFoundException;
import ua.foxminded.backend.model.institution.Faculty;
import ua.foxminded.backend.repository.institution.FacultyRepository;
import ua.foxminded.backend.service.institution.FacultyService;

@Controller
@RequestMapping("/faculty")
public class FacultyController extends BaseController<Faculty, FacultyService, FacultyRepository, FacultyNotFoundException> {
    public FacultyController(FacultyService facultyService) {
        super(facultyService);
    }

    @Override
    protected String getAttributeName() {
        return "faculties";
    }

    @Override
    protected String getPathOfTemplateOfEntity() {
        return "components/institution/faculty.html";
    }
}
