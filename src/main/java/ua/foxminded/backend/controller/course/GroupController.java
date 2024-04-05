package ua.foxminded.backend.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.backend.controller.BaseController;
import ua.foxminded.backend.exception.notfound.course.GroupNotFoundException;
import ua.foxminded.backend.model.course.Group;
import ua.foxminded.backend.repository.course.GroupRepository;
import ua.foxminded.backend.service.course.GroupService;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController<Group, GroupService, GroupRepository, GroupNotFoundException> {
    public GroupController(GroupService groupService) {
        super(groupService);
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
