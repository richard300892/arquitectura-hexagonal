package com.asociados.cope.brand;

import com.asociados.cope.BasicTest;
import com.asociados.cope.brand.model.entity.Brand;
import com.asociados.cope.exception.ExceptionValueRequired;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BrandTest {
    @Test
    void createBrand() {
        Brand brand = new BrandTestDataBuilder()
                .withId(1l)
                .withName("ZARA").reBuild();

        Assertions.assertEquals(1l, brand.getId());
        Assertions.assertEquals("ZARA", brand.getName());
    }

    @Test
    void reBuildWithOutId() {
        BasicTest.assertThrows(() -> new BrandTestDataBuilder()
                        .withName("ZARA").reBuild(),
                ExceptionValueRequired.class, "Id brand is required");
    }

    @Test
    void reBuildWithOutName() {
        BasicTest.assertThrows(() -> new BrandTestDataBuilder()
                        .withId(1l).reBuild(),
                ExceptionValueRequired.class, "Name brand is required");
    }
}