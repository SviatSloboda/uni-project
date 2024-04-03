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
import ua.foxminded.backend.exception.notfound.timetable.TimeslotNotFoundException;
import ua.foxminded.backend.model.timetable.Timeslot;
import ua.foxminded.backend.repository.timetable.TimeslotRepository;
import ua.foxminded.backend.service.BaseServiceTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TimeslotService.class, TimeslotRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/drop_tables.sql", "/sql/create_tables.sql", "/sql/insert_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
class TimeslotServiceTest extends BaseServiceTest<Timeslot, TimeslotService, TimeslotRepository, TimeslotNotFoundException> {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");
    private Timeslot timeslot;

    TimeslotServiceTest(@Autowired TimeslotService service, @Autowired TimeslotRepository repository) {
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
        timeslot = new Timeslot(UUID.randomUUID().toString(), LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDate.now());
    }

    @Override
    protected Timeslot getEntity() {
        return timeslot;
    }

    @Override
    protected String getEntityId(Timeslot entity) {
        return entity.getId();
    }

    @Override
    protected TimeslotNotFoundException getException() {
        return new TimeslotNotFoundException("There is no such Timeslot with this id!!!");
    }
}
