package ua.foxminded.backend.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.backend.model.course.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
}
