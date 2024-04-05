package ua.foxminded.backend.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ua.foxminded.backend.controller.BaseControllerTest;
import ua.foxminded.backend.exception.notfound.course.TaskNotFoundException;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.repository.course.TaskRepository;
import ua.foxminded.backend.service.course.TaskService;

@WebMvcTest(TaskController.class)
class TaskControllerTest extends BaseControllerTest<Task, TaskService, TaskRepository, TaskController, TaskNotFoundException> {

    @Autowired
    private TaskController controller;

    @Override
    protected TaskController getController() {
        return controller;
    }

    @Override
    protected String getUrlOfPageOfEntity() {
        return "/task";
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
