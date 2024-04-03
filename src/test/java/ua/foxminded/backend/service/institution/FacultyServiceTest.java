package ua.foxminded.backend.service.institution;

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
import ua.foxminded.backend.exception.illegalstate.course.CourseAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.institution.FacultyNotFoundException;
import ua.foxminded.backend.model.course.Course;
import ua.foxminded.backend.model.institution.Faculty;
import ua.foxminded.backend.repository.institution.FacultyRepository;
import ua.foxminded.backend.service.BaseServiceTest;
import ua.foxminded.backend.service.course.CourseService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {FacultyService.class, FacultyRepository.class, CourseService.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class FacultyServiceTest extends BaseServiceTest<Faculty, FacultyService, FacultyRepository, FacultyNotFoundException> {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private CourseService courseService;
    private Faculty faculty;
    private Faculty facultyForBaseServiceTests;
    private Course existingCourse;
    private Course newCourse;
    public FacultyServiceTest(@Autowired FacultyService service, @Autowired FacultyRepository repository) {
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
        faculty = facultyService.getById("1");

        facultyForBaseServiceTests = faculty.withId(UUID.randomUUID().toString());

        existingCourse = faculty.getCourses().get(0);
        newCourse = new Course("newCourseId", "New Course", (byte) 3, null, null, null, null);
        courseService.save(newCourse);
    }

    @Test
    void addCourse_AddsCourseToFaculty() {
        // act
        facultyService.addCourse(faculty.getId(), newCourse);

        // assert
        Course expectedCourse = faculty.getCourses().stream()
                .filter(course -> course.getId().equals("newCourseId"))
                .findFirst()
                .orElse(null);

        String actualId = expectedCourse.getId();
        String expectedId = newCourse.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void addCourse_AddingExistingCourseThrowsException() {
        // act & assert

        assertThrows(CourseAlreadyExistsException.class,
                () -> facultyService.addCourse("1", existingCourse));
    }

    @Test
    void removeCourseById_RemovesCourseFromFaculty() {
        // act
        Course courseToRemove = faculty.getCourses().get(0);

        facultyService.removeCourseById(faculty.getId(), courseToRemove.getId());

        // assert
        Faculty actualFaculty = facultyService.getById("1");
        boolean courseExists = actualFaculty.getCourses().stream()
                .anyMatch(course -> course.getId().equals(courseToRemove.getId()));

        assertFalse(courseExists);
    }

    @Test
    void removeCourseById_RemoveNonExistentCourseThrowsException() {
        // act & assert
        assertThrows(FacultyNotFoundException.class, () -> facultyService.removeCourseById("1", "nonExistentCourseId"));
    }

    @Override
    protected Faculty getEntity() {
        return facultyForBaseServiceTests;
    }

    @Override
    protected String getEntityId(Faculty entity) {
        return entity.getId();
    }

    @Override
    protected FacultyNotFoundException getException() {
        return new FacultyNotFoundException("There is no such Faculty!");
    }
}
