package com.asociados.cope.product;

import com.asociados.cope.BasicTest;
import com.asociados.cope.exception.ExceptionValueRequired;
import com.asociados.cope.product.model.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void createProduct() {
        Product product = new ProductTestDataBuilder()
                .withId(1l)
                .withName("TELEVISOR").reBuild();

        Assertions.assertEquals(1l, product.getId());
        Assertions.assertEquals("TELEVISOR", product.getName());
    }

    @Test
    void reBuildWithOutId() {
        BasicTest.assertThrows(() -> new ProductTestDataBuilder()
                        .withName("CARNE").reBuild(),
                ExceptionValueRequired.class, "Id product is required");
    }

    @Test
    void reBuildWithOutName() {
        BasicTest.assertThrows(() -> new ProductTestDataBuilder()
                        .withId(1l).reBuild(),
                ExceptionValueRequired.class, "Name product is required");
    }
}