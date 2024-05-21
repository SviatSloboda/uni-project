package ua.foxminded.backend.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseService<T, R extends JpaRepository<T, String>, E extends NoSuchElementException> {
    protected final R repository;

    protected BaseService(R repository) {
        this.repository = repository;
    }

    public T getById(String id) {
        return repository.findById(id).orElseThrow(() -> createNotFoundException("Entity with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<T> getAll() {
        List<T> entities = repository.findAll();

        if (entities.isEmpty()){
            throw createNotFoundException("No entities were found! List is empty!");
        }

        return entities;
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public void deleteById(String id) {
        if (!repository.existsById(id)){
            throw createNotFoundException("Entity with id " + id + " not found");
        }

        repository.deleteById(id);
    }

    protected abstract E createNotFoundException(String message);
}
