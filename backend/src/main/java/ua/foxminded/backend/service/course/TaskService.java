package ua.foxminded.backend.service.course;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.notfound.course.TaskNotFoundException;
import ua.foxminded.backend.model.course.Mark;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.course.TaskStatus;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.repository.course.TaskRepository;
import ua.foxminded.backend.service.BaseService;

@Service
public class TaskService extends BaseService<Task, TaskRepository, TaskNotFoundException> {

    protected TaskService(TaskRepository taskRepository) {
        super(taskRepository);
    }

    @Transactional
    public Task submitTask(Student student, String taskId, String answer) {
        Task taskToUpdate = student.getTasks().stream()
                .filter(task -> task.getId().equals(taskId))
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException("There is no such task with id: " + taskId));

        taskToUpdate.setUserAnswer(answer);
        taskToUpdate.setStatus(TaskStatus.UNDER_REVIEW);

        return repository.save(taskToUpdate);
    }

    @Transactional
    public Task reviewTask(String taskId, byte markValue, String feedback) {
        Task task = getById(taskId);

        task.setMark(new Mark(markValue, feedback, task.getStudent(), task));

        task.setStatus(TaskStatus.REVIEWED);

        return repository.save(task);
    }

    @Override
    protected TaskNotFoundException createNotFoundException(String message) {
        return new TaskNotFoundException(message);
    }
}
