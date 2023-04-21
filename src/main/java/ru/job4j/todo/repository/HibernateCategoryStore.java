package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateCategoryStore implements CategoryStore {
    private final CrudStore crudStore;

    @Override
    public List<Category> findAll() {
        return crudStore.query("from Category", Category.class);
    }

    @Override
    public List<Category> findByIdList(List<Integer> categoriesIds) {
        return crudStore.query("from Category where id IN :cId", Category.class, Map.of("cId", categoriesIds));
    }
}
