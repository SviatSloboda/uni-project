package ua.foxminded.backend.repository.timetable;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.backend.model.timetable.Timeslot;

public interface TimeslotRepository extends JpaRepository<Timeslot, String> {
}
