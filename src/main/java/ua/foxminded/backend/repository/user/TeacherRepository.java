package ua.foxminded.backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.backend.model.user.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
}
