package com.asociados.cope.brand.service;

import com.asociados.cope.brand.gateways.repository.RepositoryBrand;
import com.asociados.cope.brand.model.entity.Brand;

public class ServiceBrand {
    private final RepositoryBrand repositoryBrand;

    public ServiceBrand(RepositoryBrand repositoryBrand) {
        this.repositoryBrand = repositoryBrand;
    }

    public Brand getById(Long id) {
        return repositoryBrand.getById(id);
    }
}