package ua.foxminded.backend.controller.institution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ua.foxminded.backend.controller.BaseControllerTest;
import ua.foxminded.backend.exception.notfound.institution.InstituteNotFoundException;
import ua.foxminded.backend.model.institution.Institute;
import ua.foxminded.backend.repository.institution.InstituteRepository;
import ua.foxminded.backend.service.institution.InstituteService;

@WebMvcTest(InstituteController.class)
class InstituteControllerTest extends BaseControllerTest<Institute, InstituteService, InstituteRepository, InstituteController, InstituteNotFoundException> {

    @Autowired
    private InstituteController controller;

    @Override
    protected InstituteController getController() {
        return controller;
    }

    @Override
    protected String getUrlOfPageOfEntity() {
        return "/institute";
    }

    @Override
    protected String getAttributeName() {
        return "institutes";
    }

    @Override
    protected String getPathOfTemplateOfEntity() {
        return "components/institution/institute.html";
    }
}
