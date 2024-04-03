package ua.foxminded.backend.repository.user;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.backend.model.user.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
}
