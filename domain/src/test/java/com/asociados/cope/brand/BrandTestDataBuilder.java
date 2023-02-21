package com.asociados.cope.brand;

import com.asociados.cope.brand.model.entity.Brand;

public class BrandTestDataBuilder {
    private Long id;
    private String name;

    public BrandTestDataBuilder brandByFlaw() {
        this.id = 1l;
        this.name = "ZARA";

        return this;
    }

    public BrandTestDataBuilder withId(Long id) {
        this.id = id;

        return this;
    }

    public BrandTestDataBuilder withName(String name) {
        this.name = name;

        return this;
    }

    public Brand reBuild() {
        return Brand.reBuild(this.id, this.name);
    }
}