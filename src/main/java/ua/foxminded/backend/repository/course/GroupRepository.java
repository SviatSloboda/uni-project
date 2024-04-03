package ua.foxminded.backend.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.backend.model.course.Group;

public interface GroupRepository extends JpaRepository<Group, String> {
    @Override
    @Modifying
    @Query(value = "DELETE FROM Group")
    void deleteAll();
}