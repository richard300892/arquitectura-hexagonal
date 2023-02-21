package com.asociados.cope.price.adapter.dao;

import com.asociados.cope.jdbc.CustomNamedParameterJdbcTemplate;
import com.asociados.cope.jdbc.DatabaseExecution;
import com.asociados.cope.jdbc.sqlstatement.SqlStatement;
import com.asociados.cope.price.gateways.dao.DaoPrice;
import com.asociados.cope.price.model.dao.SummaryPriceRequestDTO;
import com.asociados.cope.price.model.dao.SummaryPriceResponseDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class DaoPriceMysql implements DaoPrice {
    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(nameSpace = "price", value = "getByIdProductAndIdBrand")
    private static String sqlGetByIdProductAndIdBrand;

    public DaoPriceMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public SummaryPriceResponseDTO getPriceByIdBrandAndIdProductAndDateApplication(SummaryPriceRequestDTO summaryPriceRequestDTO) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idBrand", summaryPriceRequestDTO.getIdBrand());
        mapSqlParameterSource.addValue("idProduct", summaryPriceRequestDTO.getIdProduct());
        mapSqlParameterSource.addValue("dateApplication", summaryPriceRequestDTO.getDateApplication());

        return DatabaseExecution.getObjectOrNull(() -> customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlGetByIdProductAndIdBrand, mapSqlParameterSource, new MapPriceSummary()));
    }
}