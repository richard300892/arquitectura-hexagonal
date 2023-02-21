package com.asociados.cope.product.adapter.controller;

import com.asociados.cope.product.HandlerProduct;
import com.asociados.cope.product.model.entity.Product;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@Tag(name = "Controller find product")
public class ControllerProduct {
    private final HandlerProduct handlerProduct;

    public ControllerProduct(HandlerProduct handlerProduct) {
        this.handlerProduct = handlerProduct;
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return handlerProduct.execute(id);
    }
}