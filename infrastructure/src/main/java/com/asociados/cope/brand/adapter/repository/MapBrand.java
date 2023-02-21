package com.asociados.cope.brand.adapter.repository;

import com.asociados.cope.brand.model.entity.Brand;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapBrand implements RowMapper<Brand> {
    @Override
    public Brand mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        return Brand.reBuild(id, name);
    }
}