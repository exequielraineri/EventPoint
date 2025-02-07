package com.exeraineri.eventpoint.client.data.service.implementation;

import com.exeraineri.eventpoint.client.data.service.ICategoryService;
import com.exeraineri.eventpoint.client.domain.model.Category;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    List<Category> categories = List.of(
            new Category(1l, "Deportivo"),
            new Category(2l, "Concierto"),
            new Category(3l, "Familiar"),
            new Category(4l, "Competencia"),
            new Category(5l, "Game")
    );

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categories.get(id.intValue());
    }
}
