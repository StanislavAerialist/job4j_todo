package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;

import java.util.List;

@Repository
@AllArgsConstructor
public class HibernatePriorityStore implements PriorityStore {

    private final CrudStore crudStore;

    @Override
    public List<Priority> findAll() {
        return crudStore.query("from Priority", Priority.class);
    }
}
