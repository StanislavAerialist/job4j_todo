package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserStore implements UserStore {
    private final CrudStore crudStore;

    public Optional<User> add(User user) {
       Optional<User> rsl = Optional.empty();
        try {
            crudStore.run(session -> session.persist(user));
            rsl = Optional.of(user);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return rsl;
    }

    public boolean deleteById(int id) {
        return crudStore.runForBoolean("delete from User where id = :fId",
                Map.of("fId", id)
        );
    }

    public List<User> findAll() {
        return crudStore.query("from User", User.class);
    }

    @Override
    public Optional<User> findById(int id) {
        return crudStore.optional(
                "from User where id = :tId", User.class, Map.of("tId", id));
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudStore.optional(
                "from User where login = :uLogin and password = :uPassword", User.class,
                Map.of("uLogin", login, "uPassword", password)
        );
    }
}
