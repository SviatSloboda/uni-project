package ua.foxminded.backend.repository.timetable;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.backend.model.timetable.TimetableEntry;

public interface TimetableEntryRepository extends JpaRepository<TimetableEntry, String> {
}
