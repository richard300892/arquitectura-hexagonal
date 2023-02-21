package com.asociados.cope.brand.model.entity;

import com.asociados.cope.exception.ValidateArgument;

public class Brand {
    private Long id;
    private final String name;

    private Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Brand reBuild(Long id, String name) {
        ValidateArgument.validateRequired(id, "Id brand is required");
        ValidateArgument.validateRequired(name, "Name brand is required");

        return new Brand(id, name);
    }

    public static Brand create(Long id) {
        ValidateArgument.validateRequired(id, "Id brand is required");

        return new Brand(id, "");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}