package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.HibernateUserStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateUserService implements UserService {
    private final HibernateUserStore userStore;

    @Override
    public Optional<User> add(User user) {
        return userStore.add(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userStore.findAll();
    }

    @Override
    public boolean deleteById(int id) {
        return userStore.deleteById(id);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }
}
