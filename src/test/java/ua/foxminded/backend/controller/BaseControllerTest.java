package ua.foxminded.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.backend.service.BaseService;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public abstract class BaseControllerTest<T, S extends BaseService<T, R, E>, R extends JpaRepository<T, String>, C extends BaseController<T, S, R, E>, E extends NoSuchElementException> {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected S service;

    @Test
    void testListEntities_shouldReturnViewOfEntity() throws Exception {
        mockMvc.perform(get(getUrlOfPageOfEntity()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(getAttributeName()))
                .andExpect(view().name(getPathOfTemplateOfEntity()));
    }

    @Test
    void testListEntities_shouldReturnViewOfEntityWithNoEntitiesWereFoundHeader() throws Exception {
        when(service.getAll()).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(getUrlOfPageOfEntity()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(getAttributeName()))
                .andExpect(model().attribute(getAttributeName(), Collections.emptyList()))
                .andExpect(content().string(containsString("No entities were retrieved")))
                .andExpect(view().name(getPathOfTemplateOfEntity()));
    }

    protected abstract C getController();

    protected abstract String getUrlOfPageOfEntity();

    protected abstract String getAttributeName();

    protected abstract String getPathOfTemplateOfEntity();
}
