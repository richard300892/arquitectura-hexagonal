package com.asociados.cope.brand;

import com.asociados.cope.brand.model.entity.Brand;
import com.asociados.cope.brand.service.ServiceBrand;
import org.springframework.stereotype.Component;

@Component
public class HandlerBrand {
    private final ServiceBrand serviceBrand;

    public HandlerBrand(ServiceBrand serviceBrand) {
        this.serviceBrand = serviceBrand;
    }

    public Brand execute(Long id) {
        return serviceBrand.getById(id);
    }
}