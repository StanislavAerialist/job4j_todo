package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskStore taskStore;

    public Task add(Task task) {
        return taskStore.add(task);
    }

    public boolean update(Task task) {
        return taskStore.update(task);
    }

    public boolean delete(int id) {
        return taskStore.delete(id);
    }

    public List<Task> findAll() {
        return taskStore.findAll();
    }

    public List<Task> findDone() {
        return taskStore.findDone();
    }

    public List<Task> findNew() {
        return taskStore.findNew();
    }

    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    public boolean setDone(Task task) {
        return taskStore.setDone(task);
    }
}
