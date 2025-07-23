package com.devsuperior.dscommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imgUrl;

    @ManyToMany(cascade =  CascadeType.MERGE)
    @JoinTable(name = "tb_product_category",
               joinColumns = @JoinColumn(name = "product_id"),
               inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<OrderItem> items;

}
