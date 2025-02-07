package com.exeraineri.eventpoint.client.data.service;

import com.exeraineri.eventpoint.client.domain.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);
}
