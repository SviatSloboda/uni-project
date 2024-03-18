package ua.foxminded.backend.repository.institution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.backend.model.institution.Institute;

public interface InstituteRepository extends JpaRepository<Institute, String> {
    @Override
    @Modifying
    @Query(value = "DELETE FROM Institute")
    void deleteAll();
}
