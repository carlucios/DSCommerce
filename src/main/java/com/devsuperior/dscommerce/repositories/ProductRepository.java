package com.devsuperior.dscommerce.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscommerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllBy(Pageable pageable);
    
    @EntityGraph(attributePaths = "categories")
    Optional<Product> findWithCategoriesById(Long id);
}
