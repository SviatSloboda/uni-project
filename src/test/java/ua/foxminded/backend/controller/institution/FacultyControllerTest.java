package ua.foxminded.backend.controller.institution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ua.foxminded.backend.controller.BaseControllerTest;
import ua.foxminded.backend.exception.notfound.institution.FacultyNotFoundException;
import ua.foxminded.backend.model.institution.Faculty;
import ua.foxminded.backend.repository.institution.FacultyRepository;
import ua.foxminded.backend.service.institution.FacultyService;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest extends BaseControllerTest<Faculty, FacultyService, FacultyRepository, FacultyController, FacultyNotFoundException> {

    @Autowired
    private FacultyController controller;

    @Override
    protected FacultyController getController() {
        return controller;
    }

    @Override
    protected String getUrlOfPageOfEntity() {
        return "/faculty";
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
