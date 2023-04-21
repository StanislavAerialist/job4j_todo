package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.HibernateCategoryStore;

import java.util.List;

@Service
@AllArgsConstructor
public class HibernateCategoryService implements CategoryService {
    private final HibernateCategoryStore categoryStore;

    @Override
    public List<Category> findAll() {
        return categoryStore.findAll();
    }

    @Override
    public List<Category> findByIdList(List<Integer> categoriesIds) {
        return categoryStore.findByIdList(categoriesIds);
    }
}
