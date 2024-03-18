package ua.foxminded.backend.service.timetable;

import org.springframework.stereotype.Service;
import ua.foxminded.backend.exception.notfound.timetable.TimeslotNotFoundException;
import ua.foxminded.backend.model.timetable.Timeslot;
import ua.foxminded.backend.repository.timetable.TimeslotRepository;
import ua.foxminded.backend.service.BaseService;

@Service
public class TimeslotService extends BaseService<Timeslot, TimeslotRepository, TimeslotNotFoundException> {
    protected TimeslotService(TimeslotRepository repository) {
        super(repository);
    }

    @Override
    protected TimeslotNotFoundException createNotFoundException(String message) {
        return new TimeslotNotFoundException(message);
    }
}
