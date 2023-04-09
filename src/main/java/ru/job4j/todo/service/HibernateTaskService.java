package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.HibernateTaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateTaskService implements TaskService {
    private final HibernateTaskStore taskStore;

    @Override
    public Task add(Task task) {
        return taskStore.add(task);
    }

    @Override
    public boolean update(Task task) {
        return taskStore.update(task);
    }

    @Override
    public boolean delete(int id) {
        return taskStore.delete(id);
    }

    @Override
    public List<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public List<Task> findSortedByDone(boolean done) {
        return taskStore.findSortedByDone(done);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public boolean setDone(Task task) {
        return taskStore.setDone(task);
    }
}
