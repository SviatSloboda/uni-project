package ua.foxminded.backend.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.backend.model.course.Course;

public interface CourseRepository extends JpaRepository<Course, String> {
    @Override
    @Modifying
    @Query(value = "DELETE FROM Course")
    void deleteAll();
}
