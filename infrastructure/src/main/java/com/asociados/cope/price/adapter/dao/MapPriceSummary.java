package com.asociados.cope.price.adapter.dao;

import com.asociados.cope.price.model.dao.SummaryPriceResponseDTO;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapPriceSummary implements RowMapper<SummaryPriceResponseDTO> {
    @Override
    public SummaryPriceResponseDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("id");
        Long priceList = resultSet.getLong("price_list");
        Long idBrand = resultSet.getLong("id_brand");
        Long idProduct = resultSet.getLong("id_product");

        BigDecimal price = resultSet.getBigDecimal("price");

        return new SummaryPriceResponseDTO(id, priceList, idBrand, idProduct, price);
    }
}