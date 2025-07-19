package com.devsuperior.dscommerce.mappers;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.MinProductDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categories", expression = "java(mapCategoriesToDtos(product.getCategories()))")
    ProductDTO toDto(Product product);
    MinProductDTO toMinDto(Product product);

    @Mapping(target = "categories", expression = "java(mapDtosToCategories(dto.getCategories()))")
    Product toEntity(ProductDTO dto);
    Product toEntity(MinProductDTO dto);

    @Mapping(target = "id", ignore = true)
    void update(ProductDTO dto, @MappingTarget Product entity);

    default Set<CategoryDTO> mapCategoriesToDtos(Set<Category> categories) {
        return categories == null ? Collections.emptySet() :
                categories.stream().map(entity -> {
                    return new CategoryDTO(entity.getId());
                }).collect(Collectors.toSet());
    }

    default Set<Category> mapDtosToCategories(Set<CategoryDTO> dtos) {
        return dtos == null ? Collections.emptySet() :
                dtos.stream().map(dto -> {
                    Category entity = new Category();
                    entity.setId(dto.getId());
                    return entity;
                }).collect(Collectors.toSet());
    }
}
