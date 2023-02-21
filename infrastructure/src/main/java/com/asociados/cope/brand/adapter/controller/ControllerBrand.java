package com.asociados.cope.brand.adapter.controller;

import com.asociados.cope.brand.model.entity.Brand;
import com.asociados.cope.brand.HandlerBrand;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
@Tag(name = "Controller find brand")
public class ControllerBrand {
    private final HandlerBrand handlerBrand;

    public ControllerBrand(HandlerBrand handlerBrand) {
        this.handlerBrand = handlerBrand;
    }

    @GetMapping("/{id}")
    public Brand getById(@PathVariable Long id) {
        return handlerBrand.execute(id);
    }
}