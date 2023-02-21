package com.asociados.cope.brand.adapter.repository;

import com.asociados.cope.brand.gateways.repository.RepositoryBrand;
import com.asociados.cope.brand.model.entity.Brand;
import com.asociados.cope.jdbc.CustomNamedParameterJdbcTemplate;
import com.asociados.cope.jdbc.DatabaseExecution;
import com.asociados.cope.jdbc.sqlstatement.SqlStatement;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryBrandMysql implements RepositoryBrand {
    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(nameSpace = "brand", value = "getById")
    private static String sqlGetById;

    public RepositoryBrandMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public Brand getById(Long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        return DatabaseExecution.getObjectOrNull(() -> customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlGetById, mapSqlParameterSource, new MapBrand()));
    }
}