package ua.foxminded.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @param <T> Entity
 * @param <S> Service of Entity
 * @param <R> Repository of Entity
 * @param <E> NotFoundException of Entity
 */

@Transactional
public abstract class BaseServiceTest<T, S extends BaseService<T, R, E>, R extends JpaRepository<T, String>, E extends RuntimeException> {
    S service;
    R repository;

    public BaseServiceTest(S service, R repository) {
        this.service = service;
        this.repository = repository;
    }

    @Test
    void getById_shouldReturnTheSameEntityWithIdenticalId() {
        // arrange
        T entity = getEntity();
        repository.save(entity);

        String idOfEntity = getEntityId(entity);

        // act
        T actual = service.getById(idOfEntity);

        // assert
        assertEquals(idOfEntity, getEntityId(actual));
    }

    @Test
    void getById_shouldThrowEntityNotFoundExceptionWithNotExistingId() {
        // act & assert
        assertThrows(getException().getClass(), () -> service.getById("notExistingId"));
    }

    @Test
    void deleteById_shouldReturnTheSameEntityWithIdenticalId() {
        // arrange
        T entity = getEntity();
        repository.save(entity);

        String idOfEntity = getEntityId(entity);

        // act
        service.deleteById(idOfEntity);
        Optional<T> actual = repository.findById(idOfEntity);

        // assert
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void deleteById_shouldThrowEntityNotFoundExceptionWithNotExistingId() {
        // act & assert
        assertThrows(getException().getClass(), () -> service.deleteById("notExistingId"));
    }

    @Test
    void getAll_shouldRetrieveOnlyOneElement() {
        // arrange
        repository.deleteAll();
        service.save(getEntity());

        // act
        List<T> entities = service.getAll();

        // assert
        assertEquals(1, entities.size());
    }

    @Test
    void getAll_shouldThrowEntityNotFoundExceptionWhen0EntitiesWereRetrieved() {
        // arrange
        repository.deleteAll();

        assertThrows(getException().getClass(), () -> service.getAll());
    }

    @Test
    void save_shouldSaveEntityAndGetSameId() {
        // arrange
        service.save(getEntity());
        String entityId = getEntityId(getEntity());

        // act
        T actualEntity = repository.findById(entityId).orElseThrow();

        // assert
        assertEquals(entityId, getEntityId(actualEntity));
    }

    protected abstract T getEntity();

    protected abstract String getEntityId(T entity);

    protected abstract E getException();
}
