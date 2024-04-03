package ua.foxminded.backend.service.course;

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
import ua.foxminded.backend.exception.notfound.course.MarkNotFoundException;
import ua.foxminded.backend.model.course.Mark;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.repository.course.MarkRepository;
import ua.foxminded.backend.repository.course.TaskRepository;
import ua.foxminded.backend.repository.user.StudentRepository;
import ua.foxminded.backend.service.BaseServiceTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MarkService.class, MarkRepository.class, StudentRepository.class, TaskRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class MarkServiceTest extends BaseServiceTest<Mark, MarkService, MarkRepository, MarkNotFoundException> {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private MarkService markService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Student student;
    private Mark mark;
    public MarkServiceTest(@Autowired MarkService service, @Autowired MarkRepository repository) {
        super(service, repository);
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        // Arrange
        student = studentRepository.findById("1").orElseThrow();
        Task task = taskRepository.findById("1").orElseThrow();

        mark = new Mark(UUID.randomUUID().toString(), (byte) 1, "good", null, null);
    }

    @Test
    void calculateAverageMark_ShouldReturnCorrectAverage() {
        // Arrange

        // Act
        double averageMark = markService.calculateAverageMark(student);

        // Assert
        assertEquals(85.0, averageMark);
    }

    @Test
    void getAllMarksByCourse_ShouldReturnMarksForCourse() {
        // Arrange

        // Act
        List<Mark> marks = markService.getAllMarksByCourse(student, "1");

        // Assert
        assertEquals(1, marks.size());
        assertEquals(85, marks.get(0).getMarkValue());
    }

    @Override
    protected Mark getEntity() {
        return mark;
    }

    @Override
    protected String getEntityId(Mark entity) {
        return entity.getId();
    }

    @Override
    protected MarkNotFoundException getException() {
        return new MarkNotFoundException("There is no such Mark!");
    }
}
