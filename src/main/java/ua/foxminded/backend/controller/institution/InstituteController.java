package ua.foxminded.backend.controller.institution;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.backend.controller.BaseController;
import ua.foxminded.backend.exception.notfound.institution.InstituteNotFoundException;
import ua.foxminded.backend.model.institution.Institute;
import ua.foxminded.backend.repository.institution.InstituteRepository;
import ua.foxminded.backend.service.institution.InstituteService;

@Controller
@RequestMapping("/institute")
public class InstituteController extends BaseController<Institute, InstituteService, InstituteRepository, InstituteNotFoundException> {
    public InstituteController(InstituteService instituteService) {
        super(instituteService);
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
