package ua.foxminded.backend.service.timetable;

import org.springframework.stereotype.Service;
import ua.foxminded.backend.exception.notfound.timetable.TimetableEntryNotFoundException;
import ua.foxminded.backend.model.timetable.TimetableEntry;
import ua.foxminded.backend.repository.timetable.TimetableEntryRepository;
import ua.foxminded.backend.service.BaseService;

@Service
public class TimetableEntryService extends BaseService<TimetableEntry, TimetableEntryRepository, TimetableEntryNotFoundException> {
    protected TimetableEntryService(TimetableEntryRepository repository) {
        super(repository);
    }

    @Override
    protected TimetableEntryNotFoundException createNotFoundException(String message) {
        return new TimetableEntryNotFoundException(message);
    }
}
