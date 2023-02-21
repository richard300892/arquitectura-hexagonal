package com.asociados.cope.jdbc.sqlstatement;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

public class DataAccessFieldCallback implements FieldCallback {
    private static final String EXTENSION_SQL = ".sql";
    private static final String SLASH = "/";
    private Object bean;

    public DataAccessFieldCallback(Object bean) {
        this.bean = bean;
    }

    @Override
    public void doWith(Field field) throws IllegalAccessException {
        if (!field.isAnnotationPresent(SqlStatement.class)) {
            return;
        }

        ReflectionUtils.makeAccessible(field);

        String value = field.getDeclaredAnnotation(SqlStatement.class).value();
        String namespace = field.getDeclaredAnnotation(SqlStatement.class).nameSpace();

        if (StringUtils.isBlank(value)) {
            throw new StatementException("The file name .sql must be defined");
        }

        String fileName;

        if (namespace != null && !namespace.trim().equals("")) {
            fileName = namespace.contains("sql.") ? (namespace.replace(".", SLASH) + SLASH + value)
                    : ("sql/" + namespace.replace(".", SLASH) + SLASH + value);
        } else {
            fileName = "sql/" + value;
        }

        if (!fileName.contains(EXTENSION_SQL)) {
            fileName = fileName + EXTENSION_SQL;
        }

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new StatementException("file not found [" + fileName + "]");
            }

            field.set(bean, IOUtils.toString(inputStream, StandardCharsets.UTF_8.name()));
        } catch (IOException e) {
            throw new StatementException("file not found [" + fileName + "]");
        }
    }
}