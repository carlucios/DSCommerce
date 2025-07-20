package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.mappers.CategoryMapper;
import com.devsuperior.dscommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Autowired
    CategoryMapper mapper;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> categories = repository.findAll(pageable);
        return categories.map(category -> mapper.toDto(category));
    }
}
