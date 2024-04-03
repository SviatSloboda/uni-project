package ua.foxminded.backend.service.timetable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.backend.exception.illegalstate.timetable.TimetableEntryAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.timetable.TimetableEntryNotFoundException;
import ua.foxminded.backend.exception.notfound.timetable.TimetableNotFoundException;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.model.timetable.Timeslot;
import ua.foxminded.backend.model.timetable.Timetable;
import ua.foxminded.backend.model.timetable.TimetableEntry;
import ua.foxminded.backend.repository.timetable.TimeslotRepository;
import ua.foxminded.backend.repository.timetable.TimetableEntryRepository;
import ua.foxminded.backend.repository.timetable.TimetableRepository;
import ua.foxminded.backend.service.BaseServiceTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TimetableService.class, TimetableRepository.class, TimetableEntryRepository.class, TimeslotRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class TimetableServiceTest extends BaseServiceTest<Timetable, TimetableService, TimetableRepository, TimetableNotFoundException> {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TimetableEntryRepository timetableEntryRepository;

    @Autowired
    private TimeslotRepository timeslotRepository;

    private Timetable timetable;
    private Timetable timetableForBaseServiceTests;
    private TimetableEntry timetableEntry;
    public TimetableServiceTest(@Autowired TimetableService service, @Autowired TimetableRepository repository) {
        super(service, repository);
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setup() {
        // arrange
        timetable = timetableRepository.findById("1").orElseThrow(() -> new TimetableNotFoundException("Timetable not found"));

        timetableForBaseServiceTests = new Timetable(UUID.randomUUID().toString(), null, null);

        Timeslot timeslot = new Timeslot("newTimeslotId", LocalDate.now().atStartOfDay(), LocalDate.now().atTime(10, 0), LocalDate.now());
        timeslotRepository.save(timeslot);

        timetableEntry = new TimetableEntry("newTimetableEntryId", new Lesson(), timeslot, timetable);
        timetableEntryRepository.save(timetableEntry);
    }

    @Test
    void addTimetableEntry_AddsEntryToTimetable() {
        // act
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // assert
        Timetable actualTimetable = timetableService.getById(timetable.getId());
        boolean entryAdded = actualTimetable.getEntries().stream()
                .anyMatch(entry -> entry.getId().equals(timetableEntry.getId()));

        assertTrue(entryAdded);
    }

    @Test
    void addTimetableEntry_AddingExistingEntryThrowsException() {
        // arrange
        String timetableId = timetable.getId();

        // act
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // assert
        assertThrows(TimetableEntryAlreadyExistsException.class, () -> timetableService.addTimeTableEntry(timetableId, timetableEntry));
    }

    @Test
    void removeTimetableEntry_RemovesEntryFromTimetable() {
        // act
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);
        timetableService.removeTimetableEntry(timetable.getId(), timetableEntry.getId());

        // assert
        Timetable actualTimetable = timetableService.getById(timetable.getId());
        boolean entryRemoved = actualTimetable.getEntries().stream()
                .noneMatch(entry -> entry.getId().equals(timetableEntry.getId()));

        assertTrue(entryRemoved);
    }

    @Test
    void removeTimetableEntry_RemoveNonExistentEntryThrowsException() {
        // arrange
        String timetableId = timetable.getId();
        // act & assert
        assertThrows(TimetableEntryNotFoundException.class, () -> timetableService.removeTimetableEntry(timetableId, "nonExistentEntryId"));
    }

    @Test
    void viewDailyTimetable_ReturnsLessonsForSpecificDay() {
        // arrange
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // act
        List<Lesson> lessonsForToday = timetableService.viewDailyTimetable(timetable.getId(), LocalDateTime.now());

        // assert
        assertTrue(lessonsForToday.isEmpty());
    }

    @Test
    void viewDailyTimetable_ReturnsLessonsForSpecificDayTrue() {
        // arrange
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // act
        List<Lesson> lessonsForToday = timetableService.viewDailyTimetable(timetable.getId(), LocalDateTime.of(2023, 9, 1, 8, 0, 0));

        // assert
        assertTrue(lessonsForToday.size() > 1);
    }

    @Test
    void viewWeeklyTimetable_ReturnsLessonsForSpecificWeekTrue() {
        // arrange
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // act
        List<Lesson> lessonsForThisWeek = timetableService.viewWeeklyTimetable(timetable.getId(), LocalDateTime.of(2023, 9, 1, 8, 0, 0));

        // assert
        System.out.println(lessonsForThisWeek);
        assertTrue(lessonsForThisWeek.size() > 2);
    }

    @Test
    void viewMonthlyTimetable_ReturnsLessonsForSpecificMonthTrue() {
        // arrange
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // act
        List<Lesson> lessonsForThisMonth = timetableService.viewMonthlyTimetable(timetable.getId(), LocalDateTime.of(2023, 9, 1, 8, 0, 0));

        // assert
        System.out.println(lessonsForThisMonth);
        assertTrue(lessonsForThisMonth.size() > 3);
    }

    @Test
    void viewDailyTimetable_ReturnsLessonsForCurrentDayFalse() {
        // arrange
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // act
        List<Lesson> lessonsForToday = timetableService.viewDailyTimetable(timetable.getId(), LocalDateTime.now());

        // assert
        assertTrue(lessonsForToday.isEmpty());
    }

    @Test
    void viewWeeklyTimetable_ReturnsLessonsForCurrentWeekFalse() {
        // arrange
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // act
        List<Lesson> lessonsForThisWeek = timetableService.viewWeeklyTimetable(timetable.getId(), LocalDateTime.now());

        // assert
        System.out.println(lessonsForThisWeek);
        assertTrue(lessonsForThisWeek.isEmpty());
    }

    @Test
    void viewMonthlyTimetable_ReturnsLessonsForCurrentMonthFalse() {
        // arrange
        timetableService.addTimeTableEntry(timetable.getId(), timetableEntry);

        // act
        List<Lesson> lessonsForThisMonth = timetableService.viewMonthlyTimetable(timetable.getId(), LocalDateTime.now());

        // assert
        System.out.println(lessonsForThisMonth);
        assertTrue(lessonsForThisMonth.isEmpty());
    }

    @Override
    protected Timetable getEntity() {
        return timetableForBaseServiceTests;
    }

    @Override
    protected String getEntityId(Timetable entity) {
        return entity.getId();
    }

    @Override
    protected TimetableNotFoundException getException() {
        return new TimetableNotFoundException("There is no such Timetable");
    }
}