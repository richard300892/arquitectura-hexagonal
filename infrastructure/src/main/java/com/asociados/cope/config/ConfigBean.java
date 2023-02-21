package com.asociados.cope.config;

import com.asociados.cope.brand.gateways.repository.RepositoryBrand;
import com.asociados.cope.brand.service.ServiceBrand;
import com.asociados.cope.product.gateways.repository.RepositoryProduct;
import com.asociados.cope.product.service.ServiceProduct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {
    @Bean
    public ServiceBrand getServiceBrand(RepositoryBrand repositoryBrand) {
        return new ServiceBrand(repositoryBrand);
    }

    @Bean
    public ServiceProduct getServiceProduct(RepositoryProduct repositoryProduct) {
        return new ServiceProduct(repositoryProduct);
    }
}