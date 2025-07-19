package com.devsuperior.dscommerce.mappers;

import com.devsuperior.dscommerce.dto.MinProductDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categories", expression = "java(mapCategoriesToNames(product.getCategories()))")
    ProductDTO toDto(Product product);
    MinProductDTO toMinDto(Product product);

    @Mapping(target = "categories", expression = "java(mapNamesToCategories(dto.getCategories()))")
    Product toEntity(ProductDTO dto);
    Product toEntity(MinProductDTO dto);

    default Set<String> mapCategoriesToNames(Set<Category> categories) {
        return categories == null ? null :
                categories.stream().map(Category::getName).collect(Collectors.toSet());
    }

    default Set<Category> mapNamesToCategories(Set<String> names) {
        return names == null ? null :
                names.stream().map(name -> {
                    Category c = new Category();
                    c.setName(name);
                    return c;
                }).collect(Collectors.toSet());
    }
}
