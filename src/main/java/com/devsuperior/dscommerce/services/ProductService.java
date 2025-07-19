package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.MinProductDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.mappers.ProductMapper;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Transactional(readOnly = true)
    public Page<MinProductDTO> findAll(Pageable pageable) {
        Page<Product> page = repository.findAllBy(pageable);

        return page.map(product -> mapper.toMinDto(product));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findWithCategoriesById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        return mapper.toDto(product);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            mapper.update(dto, entity);

            return mapper.toDto(repository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            Product entity = repository.getReferenceById(id);
            repository.delete(entity);
        } catch (JpaObjectRetrievalFailureException e) {
            throw new ResourceNotFoundException("Product not found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation - cannot delete product: " + id);
        }
    }
}
