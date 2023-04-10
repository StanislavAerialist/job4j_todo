package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStore {
    Optional<User> add(User user);

    Optional<User> findById(int id);

    Collection<User> findAll();

    boolean deleteById(int id);
}
