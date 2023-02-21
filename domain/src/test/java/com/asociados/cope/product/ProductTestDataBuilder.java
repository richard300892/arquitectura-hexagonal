package com.asociados.cope.product;

import com.asociados.cope.product.model.entity.Product;

public class ProductTestDataBuilder {
    private Long id;
    private String name;

    public ProductTestDataBuilder productByFlaw() {
        this.id = 1l;
        this.name = "CARNE";

        return this;
    }

    public ProductTestDataBuilder withId(Long id) {
        this.id = id;

        return this;
    }

    public ProductTestDataBuilder withName(String name) {
        this.name = name;

        return this;
    }

    public Product reBuild() {
        return Product.reBuild(this.id, this.name);
    }
}