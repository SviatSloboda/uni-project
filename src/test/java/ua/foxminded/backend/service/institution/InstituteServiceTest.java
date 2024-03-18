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
import ua.foxminded.backend.exception.illegalstate.institution.FacultyAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.institution.FacultyNotFoundException;
import ua.foxminded.backend.exception.notfound.institution.InstituteNotFoundException;
import ua.foxminded.backend.model.institution.Faculty;
import ua.foxminded.backend.model.institution.Institute;
import ua.foxminded.backend.repository.institution.InstituteRepository;
import ua.foxminded.backend.service.BaseServiceTest;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {FacultyService.class, InstituteRepository.class, InstituteService.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class InstituteServiceTest extends BaseServiceTest<Institute, InstituteService, InstituteRepository, InstituteNotFoundException> {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private FacultyService facultyService;

    private Institute institute;
    private Institute instituteForBaseServicetest;
    private Faculty newFaculty;
    private Faculty existingFaculty;
    public InstituteServiceTest(@Autowired InstituteService service, @Autowired InstituteRepository repository) {
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
        institute = instituteService.getById("1");
        instituteForBaseServicetest = new Institute(UUID.randomUUID().toString(), "test", null);

        existingFaculty = institute.getFaculties().get(0);
        newFaculty = new Faculty("newFacultyId", "New Faculty", institute, new ArrayList<>());
        facultyService.save(newFaculty);
    }

    @Test
    void addFaculty_AddsFacultyToInstitute() {
        // act
        instituteService.addFaculty(institute.getId(), newFaculty);

        // assert
        Institute actualInstitute = instituteService.getById("1");
        Optional<Faculty> addedFaculty = actualInstitute.getFaculties().stream()
                .filter(faculty -> faculty.getId().equals("newFacultyId"))
                .findFirst();

        assertTrue(addedFaculty.isPresent());
        assertEquals("New Faculty", addedFaculty.get().getName());
    }

    @Test
    void addFaculty_AddingExistingFacultyThrowsException() {
        // act & assert
        assertThrows(FacultyAlreadyExistsException.class, () -> instituteService.addFaculty("1", existingFaculty));
    }

    @Test
    void removeFacultyById_RemovesFacultyFromInstitute() {
        // act
        instituteService.removeFacultyById(institute.getId(), existingFaculty.getId());

        // assert
        Institute actualInstitute = instituteService.getById("1");
        boolean facultyExists = actualInstitute.getFaculties().stream()
                .anyMatch(faculty -> faculty.getId().equals(existingFaculty.getId()));

        assertFalse(facultyExists);
    }

    @Test
    void removeFacultyById_RemoveNonExistentFacultyThrowsException() {
        // act & assert
        assertThrows(FacultyNotFoundException.class, () -> instituteService.removeFacultyById("1", "nonExistentFacultyId"));
    }

    @Override
    protected Institute getEntity() {
        return instituteForBaseServicetest;
    }

    @Override
    protected String getEntityId(Institute entity) {
        return entity.getId();
    }

    @Override
    protected InstituteNotFoundException getException() {
        return new InstituteNotFoundException("There is no such Institute!");
    }
}
