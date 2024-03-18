package ua.foxminded.backend.repository.timetable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.backend.model.timetable.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, String> {
    @Override
    @Modifying
    @Query(value = "DELETE FROM Timetable")
    void deleteAll();
}
