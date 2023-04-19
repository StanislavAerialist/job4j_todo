package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.HibernatePriorityStore;

import java.util.List;

@Service
@AllArgsConstructor
public class HibernatePriorityService implements PriorityService {
    private final HibernatePriorityStore priorityStore;

    @Override
    public List<Priority> findAll() {
        return priorityStore.findAll();
    }
}
