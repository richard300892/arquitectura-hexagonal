package com.asociados.cope.price.model.dao;

import java.math.BigDecimal;

public class SummaryPriceResponseDTO {
    private final Long id;
    private final Long priceList;
    private final Long idBrand;
    private final Long idProduct;
    private BigDecimal price;

    public SummaryPriceResponseDTO(Long id, Long priceList, Long idBrand, Long idProduct, BigDecimal price) {
        this.id = id;
        this.priceList = priceList;
        this.idBrand = idBrand;
        this.idProduct = idProduct;
        this.price = price;

        this.calculatePrice();
    }

    private void calculatePrice() {
        BigDecimal percentageValue = (this.price.multiply(BigDecimal.valueOf(priceList)).divide(BigDecimal.valueOf(100)));

        this.price = this.price.subtract(percentageValue);
    }

    public Long getId() {
        return this.id;
    }

    public Long getPriceList() {
        return this.priceList;
    }

    public Long getIdBrand() {
        return this.idBrand;
    }

    public Long getIdProduct() {
        return this.idProduct;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}