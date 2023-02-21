package com.asociados.cope.product.gateways.repository;

import com.asociados.cope.product.model.entity.Product;

public interface RepositoryProduct {
    Product getById(Long id);
}