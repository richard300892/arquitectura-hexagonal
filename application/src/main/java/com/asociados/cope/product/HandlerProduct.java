package com.asociados.cope.product;

import com.asociados.cope.product.model.entity.Product;
import com.asociados.cope.product.service.ServiceProduct;
import org.springframework.stereotype.Component;

@Component
public class HandlerProduct {
    private final ServiceProduct serviceProduct;

    public HandlerProduct(ServiceProduct serviceProduct) {
        this.serviceProduct = serviceProduct;
    }

    public Product execute(Long id) {
        return serviceProduct.getById(id);
    }
}