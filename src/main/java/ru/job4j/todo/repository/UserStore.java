package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStore {
    Optional<User> add(User user);

    Optional<User> findById(int id);

    List<User> findAll();

    boolean deleteById(int id);

    Optional<User> findByLoginAndPassword(String login, String password);
}
