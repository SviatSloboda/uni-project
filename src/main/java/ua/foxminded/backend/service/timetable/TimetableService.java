package ua.foxminded.backend.service.timetable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.illegalstate.timetable.TimetableEntryAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.timetable.TimetableEntryNotFoundException;
import ua.foxminded.backend.exception.notfound.timetable.TimetableNotFoundException;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.model.timetable.Timetable;
import ua.foxminded.backend.model.timetable.TimetableEntry;
import ua.foxminded.backend.repository.timetable.TimetableRepository;
import ua.foxminded.backend.service.BaseService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TimetableService extends BaseService<Timetable, TimetableRepository, TimetableNotFoundException> {

    public TimetableService(TimetableRepository repository) {
        super(repository);
    }

    @Transactional
    public Timetable addTimeTableEntry(String timetableId, TimetableEntry timetableEntry) {
        Timetable timetable = getById(timetableId);

        if (timetableEntryExistsInTimetable(timetable, timetableEntry.getId())){
            throw new TimetableEntryAlreadyExistsException("TimetableEntry with id: " + timetableEntry.getId() +
                                                           " already exists in this Timetable with id: " + timetableId);
        }

        timetable.getEntries().add(timetableEntry);

        return repository.save(timetable);
    }

    @Transactional
    public Timetable removeTimetableEntry(String timetableId, String timetableEntryId) {
        Timetable timetable = getById(timetableId);

        TimetableEntry timetableEntryToRemove = timetable.getEntries().stream()
                .filter(timetableEntry -> timetableEntry.getId().equals(timetableEntryId))
                .findFirst()
                .orElseThrow(() -> new TimetableEntryNotFoundException("No Timetable entry with id: " + timetableEntryId));

        timetable.getEntries().remove(timetableEntryToRemove);

        return repository.save(timetable);
    }


    @Transactional(readOnly = true)
    public List<Lesson> viewDailyTimetable(String timetableId, LocalDateTime startDateTime) {
        Timetable timetable = getById(timetableId);

        return timetable.getEntries().stream()
                .filter(entry -> !entry.getTimeslot().getStartTime().isBefore(startDateTime))
                .sorted(Comparator.comparing(entry -> entry.getTimeslot().getStartTime()))
                .map(TimetableEntry::getLesson)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Lesson> viewWeeklyTimetable(String timetableId, LocalDateTime startDateTime) {
        Timetable timetable = getById(timetableId);

        return timetable.getEntries().stream()
                .filter(entry -> !entry.getTimeslot().getStartTime().isBefore(startDateTime) &&
                                 entry.getTimeslot().getLocalDate().isBefore(startDateTime.toLocalDate().plusWeeks(1)))
                .sorted(Comparator.comparing(entry -> entry.getTimeslot().getStartTime()))
                .map(TimetableEntry::getLesson)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Lesson> viewMonthlyTimetable(String timetableId, LocalDateTime startDateTime) {
        Timetable timetable = getById(timetableId);

        return timetable.getEntries().stream()
                .filter(entry -> !entry.getTimeslot().getStartTime().isBefore(startDateTime) &&
                                 entry.getTimeslot().getLocalDate().isBefore(startDateTime.toLocalDate().plusMonths(1)))
                .sorted(Comparator.comparing(entry -> entry.getTimeslot().getStartTime()))
                .map(TimetableEntry::getLesson)
                .toList();
    }

    private boolean timetableEntryExistsInTimetable(Timetable timetable, String timetableEntryId) {
        return timetable.getEntries().stream()
                .anyMatch(entry -> entry.getId().equals(timetableEntryId));
    }

    @Override
    protected TimetableNotFoundException createNotFoundException(String message) {
        return new TimetableNotFoundException(message);
    }
}
