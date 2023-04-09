package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task add(Task task);
    boolean update(Task task);
    boolean delete(int id);
    List<Task> findAll();
    List<Task> findSortedByDone(boolean done);
    Optional<Task> findById(int id);
    boolean setDone(Task task);
}
