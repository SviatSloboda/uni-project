package ua.foxminded.backend.service.user;

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
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.user.Teacher;
import ua.foxminded.backend.model.user.User;
import ua.foxminded.backend.repository.user.TeacherRepository;
import ua.foxminded.backend.service.BaseServiceTest;
import ua.foxminded.backend.service.course.TaskService;
import ua.foxminded.backend.service.timetable.TimetableService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TeacherService.class, TeacherRepository.class, TaskService.class, TimetableService.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class TeacherServiceTest extends BaseServiceTest<Teacher, TeacherService, TeacherRepository, TeacherNotFoundException> {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private TeacherService teacherService;

    private Teacher teacher;
    private Teacher teacherForBaseServiceTest;

    public TeacherServiceTest(@Autowired TeacherService service, @Autowired TeacherRepository repository) {
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
        teacher = teacherService.getById("1");

        teacherForBaseServiceTest = new Teacher(UUID.randomUUID().toString(), null, null);
    }

    @Test
    void saveTeacher_ShouldSaveTeacherCorrectly() {
        // arrange
        User newUser = new User("New", "User", "newUserLogin", "newUserPassword");

        // act
        Teacher savedTeacher = teacherService.saveTeacher(new Teacher("1", null, null), newUser);

        // assert
        assertNotNull(savedTeacher);
        assertEquals("New", savedTeacher.getFirstName());
        assertEquals("User", savedTeacher.getLastName());
    }

    @Test
    void reviewTask_ShouldReviewTaskCorrectly() {
        // arrange
        String taskId = "1";
        byte markValue = 85;
        String feedback = "Well done!";

        // act
        Task reviewedTask = teacherService.reviewTask(taskId, markValue, feedback);

        // assert
        assertNotNull(reviewedTask.getMark());
        assertEquals(markValue, reviewedTask.getMark().getMarkValue());
        assertEquals(feedback, reviewedTask.getMark().getFeedback());
    }

    @Test
    void viewDailyTimetable_shouldRetrieveLessons() {
        // arrange & act
        List<Lesson> dailyLessons = teacherService.viewTimeTableByPeriod(teacher.getId(), LocalDateTime.of(2023, 9, 1, 8, 0), "day");

        // assert
        assertNotNull(dailyLessons);
        assertFalse(dailyLessons.isEmpty());
    }

    @Test
    void viewWeeklyTimetable_shouldRetrieveLessons() {
        // arrange & act
        List<Lesson> weeklyLessons = teacherService.viewTimeTableByPeriod(teacher.getId(), LocalDateTime.of(2023, 9, 1, 8, 0), "week");

        // assert
        assertNotNull(weeklyLessons);
        assertTrue(weeklyLessons.size() > 2);
    }

    @Test
    void viewMonthlyTimetable_shouldRetrieveLessons() {
        // arrange & act
        List<Lesson> monthlyLessons = teacherService.viewTimeTableByPeriod(teacher.getId(), LocalDateTime.of(2023, 9, 1, 8, 0), "month");

        // assert
        assertNotNull(monthlyLessons);
        assertTrue(monthlyLessons.size() > 3);
    }

    @Test
    void viewTimetable_shouldThrowIllegalStateExceptionWhenPeriodIsWronglySet() {
        // arrange
        String teacherId = teacher.getId();

        // act & assert
        assertThrows(IllegalStateException.class, () -> teacherService.viewTimeTableByPeriod(teacherId,
                        null, "century"),
                "You have set wrong period!"
        );
    }

    @Override
    protected Teacher getEntity() {
        return teacherForBaseServiceTest;
    }

    @Override
    protected String getEntityId(Teacher entity) {
        return entity.getId();
    }

    @Override
    protected TeacherNotFoundException getException() {
        return new TeacherNotFoundException("There is no such Teacher!");
    }
}
