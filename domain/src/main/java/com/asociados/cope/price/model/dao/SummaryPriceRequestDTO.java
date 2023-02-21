package com.asociados.cope.price.model.dao;

import java.time.LocalDateTime;

public class SummaryPriceRequestDTO {
    private final Long idBrand;
    private final Long idProduct;
    private final LocalDateTime dateApplication;

    public SummaryPriceRequestDTO(Long idBrand, Long idProduct, LocalDateTime dateApplication) {
        this.idBrand = idBrand;
        this.idProduct = idProduct;
        this.dateApplication = dateApplication;
    }

    public Long getIdBrand() {
        return idBrand;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public LocalDateTime getDateApplication() {
        return dateApplication;
    }
}