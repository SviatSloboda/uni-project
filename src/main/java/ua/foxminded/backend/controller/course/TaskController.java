package ua.foxminded.backend.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.backend.controller.BaseController;
import ua.foxminded.backend.exception.notfound.course.TaskNotFoundException;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.repository.course.TaskRepository;
import ua.foxminded.backend.service.course.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController<Task, TaskService, TaskRepository, TaskNotFoundException> {
    public TaskController(TaskService taskService) {
        super(taskService);
    }

    @Override
    protected String getAttributeName() {
        return "tasks";
    }

    @Override
    protected String getPathOfTemplateOfEntity() {
        return "components/course/task.html";
    }
}
