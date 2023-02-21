package com.asociados.cope.product.service;

import com.asociados.cope.product.gateways.repository.RepositoryProduct;
import com.asociados.cope.product.model.entity.Product;

public class ServiceProduct {
    private final RepositoryProduct repositoryProduct;

    public ServiceProduct(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    public Product getById(Long id) {
        return repositoryProduct.getById(id);
    }
}