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
import ua.foxminded.backend.exception.illegalstate.course.GroupAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.course.TaskAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.user.TeacherAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.course.CourseNotFoundException;
import ua.foxminded.backend.exception.notfound.course.GroupNotFoundException;
import ua.foxminded.backend.exception.notfound.course.TaskNotFoundException;
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;
import ua.foxminded.backend.model.course.Course;
import ua.foxminded.backend.model.course.Group;
import ua.foxminded.backend.model.course.Task;
import ua.foxminded.backend.model.course.TaskStatus;
import ua.foxminded.backend.model.user.Teacher;
import ua.foxminded.backend.repository.course.CourseRepository;
import ua.foxminded.backend.repository.course.GroupRepository;
import ua.foxminded.backend.repository.course.TaskRepository;
import ua.foxminded.backend.repository.user.TeacherRepository;
import ua.foxminded.backend.service.BaseServiceTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CourseService.class, CourseRepository.class, GroupRepository.class, TaskRepository.class, TeacherRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class CourseServiceTest extends BaseServiceTest<Course, CourseService, CourseRepository, CourseNotFoundException> {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private Course course;
    private Course courseForBaseServiceTest;
    private Teacher teacher;
    private Group group;
    private Task task;

    public CourseServiceTest(@Autowired CourseService service, @Autowired CourseRepository repository) {
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
        course = new Course("courseId", "Test Course", (byte) 4, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        course = courseRepository.save(course);

        courseForBaseServiceTest = new Course(UUID.randomUUID().toString(), "test", (byte) 1, null, null, null, null);

        teacher = new Teacher("teacherId", Collections.emptyList(), Collections.emptyList());
        teacher = teacherRepository.save(teacher);

        group = new Group("groupId", "Test Group", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null);
        group = groupRepository.save(group);

        task = new Task("taskId", "Test Task", "Description", true, LocalDateTime.now(), "userAnswer", TaskStatus.NOT_COMPLETED, course, null, null);
        task = taskRepository.save(task);
    }

    @Test
    void addTeacherToCourse_ShouldAddTeacher() {
        // Arrange & Act
        courseService.addTeacherToCourse(course.getId(), teacher);

        // Assert
        Course updatedCourse = courseRepository.findById(course.getId()).orElseThrow();
        assertTrue(updatedCourse.getTeachers().contains(teacher));
    }

    @Test
    void addTeacherToCourse_WhenTeacherAlreadyExists_ShouldThrowException() {
        // Arrange
        String courseId = course.getId();

        course.getTeachers().add(teacher);
        courseRepository.save(course);

        // Act & Assert
        assertThrows(TeacherAlreadyExistsException.class, () -> courseService.addTeacherToCourse(courseId, teacher));
    }

    @Test
    void removeTeacherFromCourseById_ShouldRemoveTeacher() {
        // Arrange
        course.getTeachers().add(teacher);
        courseRepository.save(course);

        // Act
        courseService.removeTeacherFromCourseById(course.getId(), teacher.getId());

        // Assert
        Course updatedCourse = courseRepository.findById(course.getId()).orElseThrow();
        assertFalse(updatedCourse.getTeachers().contains(teacher));
    }

    @Test
    void removeTeacherFromCourseById_WhenTeacherNotFound_ShouldThrowException() {
        // Arrange
        String courseId = course.getId();

        // Act & Assert
        assertThrows(TeacherNotFoundException.class, () -> courseService.removeTeacherFromCourseById(courseId, "nonExistentTeacherId"));
    }

    @Test
    void addGroupToCourse_ShouldAddGroup() {
        // Arrange & Act
        courseService.addGroupToCourse(course.getId(), group);

        // Assert
        Course updatedCourse = courseRepository.findById(course.getId()).orElseThrow();
        assertTrue(updatedCourse.getGroups().contains(group));
    }

    @Test
    void addGroupToCourse_WhenGroupAlreadyExists_ShouldThrowException() {
        // Arrange
        String courseId = course.getId();
        course.getGroups().add(group);
        courseRepository.save(course);

        // Act & Assert
        assertThrows(GroupAlreadyExistsException.class, () -> courseService.addGroupToCourse(courseId, group));
    }

    @Test
    void removeGroupFromCourse_ShouldRemoveGroup() {
        // Arrange
        course.getGroups().add(group);
        courseRepository.save(course);

        // Act
        courseService.removeGroupFromCourse(course.getId(), group.getId());

        // Assert
        Course updatedCourse = courseRepository.findById(course.getId()).orElseThrow();
        assertFalse(updatedCourse.getGroups().contains(group));
    }

    @Test
    void removeGroupFromCourse_WhenGroupNotFound_ShouldThrowException() {
        String courseId = course.getId();
        // Act & Assert
        assertThrows(GroupNotFoundException.class, () -> courseService.removeGroupFromCourse(courseId, "nonExistentGroupId"));
    }

    @Test
    void addTaskToCourse_ShouldAddTask() {
        // Arrange

        // Act
        courseService.addTaskToCourse(course.getId(), task);

        // Assert
        Course updatedCourse = courseRepository.findById(course.getId()).orElseThrow();
        assertTrue(updatedCourse.getTasks().contains(task));
    }

    @Test
    void addTaskToCourse_WhenTaskAlreadyExists_ShouldThrowException() {
        // Arrange
        String courseId = course.getId();

        course.getTasks().add(task);
        courseRepository.save(course);

        // Act & Assert
        assertThrows(TaskAlreadyExistsException.class, () -> courseService.addTaskToCourse(courseId, task));
    }

    @Test
    void removeTaskFromCourseById_ShouldRemoveTask() {
        // Arrange
        course.getTasks().add(task);
        courseRepository.save(course);

        // Act
        courseService.removeTaskFromCourseById(course.getId(), task.getId());

        // Assert
        Course updatedCourse = courseRepository.findById(course.getId()).orElseThrow();
        assertFalse(updatedCourse.getTasks().contains(task));
    }

    @Test
    void removeTaskFromCourseById_WhenTaskNotFound_ShouldThrowException() {
        String courseId = course.getId();
        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> courseService.removeTaskFromCourseById(courseId, "nonExistentTaskId"));
    }

    @Override
    protected Course getEntity() {
        return courseForBaseServiceTest;
    }

    @Override
    protected String getEntityId(Course entity) {
        return entity.getId();
    }

    @Override
    protected CourseNotFoundException getException() {
        return new CourseNotFoundException("There is no such course!");
    }
}
