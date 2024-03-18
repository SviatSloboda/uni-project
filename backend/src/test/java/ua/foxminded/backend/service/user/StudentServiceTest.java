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
import ua.foxminded.backend.exception.notfound.user.StudentNotFoundException;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.model.course.Mark;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.model.user.User;
import ua.foxminded.backend.repository.user.StudentRepository;
import ua.foxminded.backend.service.BaseServiceTest;
import ua.foxminded.backend.service.course.MarkService;
import ua.foxminded.backend.service.course.TaskService;
import ua.foxminded.backend.service.timetable.TimetableService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {StudentService.class, StudentRepository.class, MarkService.class, TaskService.class, TimetableService.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class StudentServiceTest extends BaseServiceTest<Student, StudentService, StudentRepository, StudentNotFoundException> {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private StudentService studentService;

    private Student student;
    private Student studentForBaseServiceTest;
    public StudentServiceTest(@Autowired StudentService service, @Autowired StudentRepository repository) {
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
        student = studentService.getById("1");

        studentForBaseServiceTest = new Student(UUID.randomUUID().toString(), null, null);
    }

    @Test
    void calculateGPAOfStudent_ShouldCalculateCorrectly() {
        // arrange
        // act
        double gpa = studentService.calculateGPAOfStudent(student.getId());

        // assert
        assertTrue(gpa > 0);
    }

    @Test
    void submitTask_ShouldSubmitTaskCorrectly() {
        // arrange
        String taskId = "1";
        String answer = "Test Answer";

        // act
        studentService.submitTask(student.getId(), taskId, answer);

        // assert
        Student updatedStudent = studentService.getById(student.getId());
        assertTrue(updatedStudent.getTasks().stream().anyMatch(task -> task.getUserAnswer().equals(answer)));
    }

    @Test
    void viewDailyTimetable_ShouldReturnLessonsForToday() {
        // arrange
        LocalDateTime now = LocalDateTime.now();

        // act
        List<Lesson> lessons = studentService.viewDailyTimetable(student.getId(), now);

        // assert
        assertNotNull(lessons);
    }

    @Test
    void viewWeeklyTimetable_ShouldReturnLessonsForThisWeek() {
        // arrange
        LocalDateTime startOfWeek = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        // act
        List<Lesson> lessons = studentService.viewWeeklyTimetable(student.getId(), startOfWeek);

        // assert
        assertNotNull(lessons);
    }

    @Test
    void viewMonthlyTimetable_ShouldReturnLessonsForThisMonth() {
        // arrange
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        // act
        List<Lesson> lessons = studentService.viewMonthlyTimetable(student.getId(), startOfMonth);

        // assert
        assertNotNull(lessons);
    }

    @Test
    void getAllMarksOfStudent_ShouldReturnAllMarks() {
        // Given
        String studentId = "1";

        // When
        List<Mark> marks = studentService.getAllMarksOfStudent(studentId);

        // Then
        assertNotNull(marks);
        assertFalse(marks.isEmpty());
    }

    @Test
    void getAllMarksOfStudentByCourseId_ShouldReturnAllMarksForCourse() {
        // Given
        String studentId = "1";
        String courseId = "1";

        // When
        List<Mark> marks = studentService.getAllMarksOfStudentByCourseId(studentId, courseId);

        // Then
        assertNotNull(marks);
        assertFalse(marks.isEmpty());
    }

    @Test
    void getAllTasksOfStudent_ShouldReturnAllTasks() {
        // Given
        String studentId = "1";

        // When
        List<Task> tasks = studentService.getAllTasksOfStudent(studentId);

        // Then
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
    }

    @Test
    void saveStudent_ShouldSaveTeacherCorrectly() {
        // arrange
        User newUser = new User("New", "User", "newUserLogin", "newUserPassword");

        // act
        Student savedStudent = studentService.saveStudent(new Student("1", null, null), newUser);

        // assert
        assertNotNull(savedStudent);
        assertEquals("New", savedStudent.getFirstName());
        assertEquals("User", savedStudent.getLastName());
    }

    @Override
    protected Student getEntity() {
        return studentForBaseServiceTest;
    }

    @Override
    protected String getEntityId(Student entity) {
        return entity.getId();
    }

    @Override
    protected StudentNotFoundException getException() {
        return new StudentNotFoundException("There is no such Student!");
    }
}
