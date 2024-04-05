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
import ua.foxminded.backend.exception.notfound.course.TaskNotFoundException;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.course.TaskCharacteristics;
import ua.foxminded.backend.model.course.TaskStatus;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.repository.course.TaskRepository;
import ua.foxminded.backend.repository.user.StudentRepository;
import ua.foxminded.backend.service.BaseServiceTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TaskService.class, TaskRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class TaskServiceTest extends BaseServiceTest<Task, TaskService, TaskRepository, TaskNotFoundException> {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private TaskService taskService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Student student;
    private Task task;
    private Task taskForBaseServiceTest;
    public TaskServiceTest(@Autowired TaskService service, @Autowired TaskRepository repository) {
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
        student = studentRepository.findById("1").orElseThrow();
        task = taskRepository.findById("1").orElseThrow();

        taskForBaseServiceTest = new Task(UUID.randomUUID().toString(), new TaskCharacteristics("test", "test", true, LocalDateTime.now(), "test", TaskStatus.NOT_COMPLETED), null, null, null);
    }

    @Test
    void submitTask_ShouldUpdateTask() {
        // Arrange

        // Act
        String newAnswer = "Updated answer";
        taskService.submitTask(student, task.getId(), newAnswer);

        // Assert
        Task updatedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertEquals(TaskStatus.UNDER_REVIEW, updatedTask.getStatus());
        assertEquals(newAnswer, updatedTask.getUserAnswer());
    }

    @Test
    void reviewTask_ShouldSetTaskToReviewedAndAssignMark() {
        // Arrange

        // Act
        byte markValue = 95;
        String feedback = "Excellent work!";
        taskService.reviewTask(task.getId(), markValue, feedback);

        // Assert
        Task reviewedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertEquals(TaskStatus.REVIEWED, reviewedTask.getStatus());
        assertNotNull(reviewedTask.getMark());
        assertEquals(markValue, reviewedTask.getMark().getMarkValue());
        assertEquals(feedback, reviewedTask.getMark().getFeedback());
    }

    @Override
    protected Task getEntity() {
        return taskForBaseServiceTest;
    }

    @Override
    protected String getEntityId(Task entity) {
        return entity.getId();
    }

    @Override
    protected TaskNotFoundException getException() {
        return new TaskNotFoundException("There is no such Task!");
    }
}
