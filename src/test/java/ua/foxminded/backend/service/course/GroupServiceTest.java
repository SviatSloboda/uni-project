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
import ua.foxminded.backend.exception.illegalstate.course.LessonAlreadyExistsException;
import ua.foxminded.backend.exception.illegalstate.user.StudentAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.course.GroupNotFoundException;
import ua.foxminded.backend.exception.notfound.course.LessonNotFoundException;
import ua.foxminded.backend.exception.notfound.user.TeacherNotFoundException;
import ua.foxminded.backend.model.course.Group;
import ua.foxminded.backend.model.course.Lesson;
import ua.foxminded.backend.model.user.Student;
import ua.foxminded.backend.model.user.Teacher;
import ua.foxminded.backend.repository.course.GroupRepository;
import ua.foxminded.backend.repository.course.LessonRepository;
import ua.foxminded.backend.repository.user.StudentRepository;
import ua.foxminded.backend.service.BaseServiceTest;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {GroupService.class, GroupRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class GroupServiceTest extends BaseServiceTest<Group, GroupService, GroupRepository, GroupNotFoundException> {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    private Group group;
    private Group groupForBaseServiceTest;
    private Student student;
    private Lesson lesson;
    public GroupServiceTest(@Autowired GroupService service, @Autowired GroupRepository repository) {
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
        group = new Group("groupId", "Test Group", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null);
        group = groupRepository.save(group);

        groupForBaseServiceTest = new Group(UUID.randomUUID().toString(), "test", null, null, null, null);

        student = new Student("studentId", null, Collections.emptyList());
        student = studentRepository.save(student);

        lesson = new Lesson("lessonId", "Test Lesson", "Room 101", null, new Teacher(), group);
        lesson = lessonRepository.save(lesson);
    }

    @Test
    void addStudentToGroup_ShouldAddStudent() {
        // Arrange

        // Act
        groupService.addStudentToGroup(group.getId(), student);

        // Assert
        Group updatedGroup = groupRepository.findById(group.getId()).orElseThrow();
        assertTrue(updatedGroup.getStudents().contains(student));
    }

    @Test
    void addStudentToGroup_WhenStudentAlreadyExists_ShouldThrowException() {
        // Arrange
        String groupId = group.getId();

        group.getStudents().add(student);
        groupRepository.save(group);

        // Act & Assert
        assertThrows(StudentAlreadyExistsException.class, () -> groupService.addStudentToGroup(groupId, student));
    }

    @Test
    void removeStudentFromGroupById_ShouldRemoveStudent() {
        // Arrange
        group.getStudents().add(student);
        groupRepository.save(group);

        // Act
        groupService.removeStudentFromGroupById(group.getId(), student.getId());

        // Assert
        Group updatedGroup = groupRepository.findById(group.getId()).orElseThrow();
        assertFalse(updatedGroup.getStudents().contains(student));
    }

    @Test
    void removeStudentFromGroupById_WhenStudentNotFound_ShouldThrowException() {
        // Arrange
        String groupId = group.getId();

        // Act & Assert
        assertThrows(TeacherNotFoundException.class, () -> groupService.removeStudentFromGroupById(groupId, "nonExistentStudentId"));
    }

    @Test
    void addLessonToGroup_ShouldAddLesson() {
        // Arrange
        Lesson newLesson = new Lesson("newLessonId", "New Test Lesson", "Room 102", null, new Teacher(), new Group());
        lessonRepository.save(newLesson);

        // Act
        groupService.addLessonToGroup(group.getId(), newLesson);

        // Assert
        Group updatedGroup = groupRepository.findById(group.getId()).orElseThrow();
        Lesson lesson = updatedGroup.getLessons().stream()
                .filter(lessonToFind -> lessonToFind.getId().equals("newLessonId"))
                .findAny()
                .orElseThrow();

        boolean lessonIdIsIdentical = lesson.getId().equals("newLessonId");
        assertTrue(lessonIdIsIdentical);
    }

    @Test
    void addLessonToGroup_WhenLessonAlreadyExists_ShouldThrowException() {
        // Arrange
        String groupId = group.getId();
        group.getLessons().add(lesson);
        groupRepository.save(group);

        // Act & Assert
        assertThrows(LessonAlreadyExistsException.class, () -> groupService.addLessonToGroup(groupId, lesson));
    }

    @Test
    void removeLessonFromGroupById_ShouldRemoveLesson() {
        // Arrange
        group.getLessons().add(lesson);
        groupRepository.save(group);

        // Act
        groupService.removeLessonFromGroupById(group.getId(), lesson.getId());

        // Assert
        Group updatedGroup = groupRepository.findById(group.getId()).orElseThrow();
        assertFalse(updatedGroup.getLessons().contains(lesson));
    }

    @Test
    void removeLessonFromGroupById_WhenLessonNotFound_ShouldThrowException() {
        // Arrange
        String groupId = group.getId();
        // Act & Assert
        assertThrows(LessonNotFoundException.class, () -> groupService.removeLessonFromGroupById(groupId, "nonExistentLessonId"));
    }

    @Override
    protected Group getEntity() {
        return groupForBaseServiceTest;
    }

    @Override
    protected String getEntityId(Group entity) {
        return entity.getId();
    }

    @Override
    protected GroupNotFoundException getException() {
        return new GroupNotFoundException("There is no such Group!");
    }
}
