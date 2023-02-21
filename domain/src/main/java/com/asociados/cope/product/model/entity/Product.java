package com.asociados.cope.product.model.entity;

import com.asociados.cope.exception.ValidateArgument;

public class Product {
    private Long id;
    private final String name;

    private Product(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Product reBuild(Long id, String name) {
        ValidateArgument.validateRequired(id, "Id product is required");
        ValidateArgument.validateRequired(name, "Name product is required");

        return new Product(id, name);
    }

    public static Product create(Long id) {
        ValidateArgument.validateRequired(id, "Id product is required");

        return new Product(id, "");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}