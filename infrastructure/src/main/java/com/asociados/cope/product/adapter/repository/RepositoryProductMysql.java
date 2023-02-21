package com.asociados.cope.product.adapter.repository;

import com.asociados.cope.brand.adapter.repository.MapBrand;
import com.asociados.cope.jdbc.CustomNamedParameterJdbcTemplate;
import com.asociados.cope.jdbc.DatabaseExecution;
import com.asociados.cope.jdbc.sqlstatement.SqlStatement;
import com.asociados.cope.product.gateways.repository.RepositoryProduct;
import com.asociados.cope.product.model.entity.Product;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryProductMysql implements RepositoryProduct {
    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(nameSpace = "product", value = "getById")
    private static String sqlGetById;

    public RepositoryProductMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public Product getById(Long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        return DatabaseExecution.getObjectOrNull(() -> customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlGetById, mapSqlParameterSource, new MapProduct()));
    }
}