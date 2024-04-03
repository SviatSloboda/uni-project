package ua.foxminded.backend.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.backend.model.course.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, String> {
}
