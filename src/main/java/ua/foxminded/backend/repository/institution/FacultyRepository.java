package ua.foxminded.backend.repository.institution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.backend.model.institution.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, String> {
    @Override
    @Modifying
    @Query(value = "DELETE FROM Faculty")
    void deleteAll();
}
