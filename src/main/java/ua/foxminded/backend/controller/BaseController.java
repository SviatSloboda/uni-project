package ua.foxminded.backend.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.foxminded.backend.service.BaseService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseController<T, S extends BaseService<T, R, E>, R extends JpaRepository<T, String>, E extends NoSuchElementException> {
    private final S service;
    private List<T> entities = Collections.emptyList();

    protected BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public String listTasks(Model model) {
        try {
            entities = service.getAll();
        } catch (NoSuchElementException e) {
            System.out.println("No elements found");
        }

        model.addAttribute(getAttributeName(), entities);

        return getPathOfTemplateOfEntity();
    }

    protected abstract String getAttributeName();

    protected abstract String getPathOfTemplateOfEntity();
}
