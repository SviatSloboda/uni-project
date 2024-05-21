package ua.foxminded.backend.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ua.foxminded.backend.controller.BaseControllerTest;
import ua.foxminded.backend.exception.notfound.course.GroupNotFoundException;
import ua.foxminded.backend.model.course.Group;
import ua.foxminded.backend.repository.course.GroupRepository;
import ua.foxminded.backend.service.course.GroupService;

@WebMvcTest(GroupController.class)
class GroupControllerTest extends BaseControllerTest<Group, GroupService, GroupRepository, GroupController, GroupNotFoundException> {

    @Autowired
    private GroupController controller;

    @Override
    protected GroupController getController() {
        return controller;
    }

    @Override
    protected String getUrlOfPageOfEntity() {
        return "/group";
    }

    @Override
    protected String getAttributeName() {
        return "groups";
    }

    @Override
    protected String getPathOfTemplateOfEntity() {
        return "components/course/group.html";
    }
}
