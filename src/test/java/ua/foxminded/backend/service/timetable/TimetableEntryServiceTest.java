package ua.foxminded.backend.service.timetable;

import org.junit.jupiter.api.BeforeEach;
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
import ua.foxminded.backend.exception.notfound.timetable.TimetableEntryNotFoundException;
import ua.foxminded.backend.model.timetable.TimetableEntry;
import ua.foxminded.backend.repository.timetable.TimetableEntryRepository;
import ua.foxminded.backend.service.BaseServiceTest;

import java.util.UUID;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TimetableEntryService.class, TimetableEntryRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class TimetableEntryServiceTest extends BaseServiceTest<TimetableEntry, TimetableEntryService, TimetableEntryRepository, TimetableEntryNotFoundException> {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");
    private TimetableEntry timetableEntry;

    public TimetableEntryServiceTest(@Autowired TimetableEntryService service, @Autowired TimetableEntryRepository repository) {
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
        timetableEntry = new TimetableEntry(UUID.randomUUID().toString(), null, null, null);
    }

    @Override
    protected TimetableEntry getEntity() {
        return timetableEntry;
    }

    @Override
    protected String getEntityId(TimetableEntry entity) {
        return entity.getId();
    }

    @Override
    protected TimetableEntryNotFoundException getException() {
        return new TimetableEntryNotFoundException("There is no such TimetableEntry!");
    }
}
