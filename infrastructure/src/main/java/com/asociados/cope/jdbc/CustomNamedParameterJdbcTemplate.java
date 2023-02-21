package com.asociados.cope.jdbc;

import com.asociados.cope.exception.ExceptionTechnique;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Repository
public class CustomNamedParameterJdbcTemplate {
    private static final String ERROR_GETTING_NAME_AND_VALUE = "Error getting object name and value";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private MapSqlParameterSource createParam(Object object) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();

        Field[] fields = object.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            try {
                Field field = fields[i];

                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);

                    paramSource.addValue(field.getName(), field.get(object));

                    field.setAccessible(false);
                }
            } catch (Exception e) {
                throw new ExceptionTechnique(ERROR_GETTING_NAME_AND_VALUE, e);
            }
        }

        return paramSource;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
}