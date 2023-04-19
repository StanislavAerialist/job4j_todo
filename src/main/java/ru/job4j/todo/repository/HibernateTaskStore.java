package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private final CrudStore crudStore;

    @Override
    public Task add(Task task) {
        crudStore.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean update(Task task) {
        return crudStore.runForBoolean(session -> {
            session.merge(task);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return crudStore.runForBoolean("delete from Task where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public List<Task> findAll() {
        return crudStore.query("from Task as t JOIN FETCH t.priority", Task.class);
    }

    @Override
    public List<Task> findSortedByDone(boolean done) {
        return crudStore.query("from Task as t JOIN FETCH t.priority where t.done = :tDone", Task.class,
                Map.of("tDone", done)
        );
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudStore.optional("from Task as t JOIN FETCH t.priority where t.id = :tId", Task.class,
                Map.of("tId", id));
    }

    @Override
    public boolean setDone(int id) {
        return crudStore.runForBoolean("UPDATE Task SET done = :tDone where id = :tId",
                Map.of("tDone", true, "tId", id));
    }
}