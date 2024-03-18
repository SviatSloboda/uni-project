package ua.foxminded.backend.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.backend.model.course.Mark;

public interface MarkRepository extends JpaRepository<Mark, String> {
    @Override
    @Modifying
    @Query(value = "DELETE FROM Mark")
    void deleteAll();
}
