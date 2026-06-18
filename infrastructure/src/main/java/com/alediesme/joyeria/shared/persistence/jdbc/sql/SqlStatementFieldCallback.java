package com.alediesme.joyeria.shared.persistence.jdbc.sql;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SqlStatementFieldCallback implements ReflectionUtils.FieldCallback {

  private static final String EXTENSION_SQL = ".sql";
  private static final String SLASH = "/";

  private final Object bean;
  private final String sqlDialect;

  public SqlStatementFieldCallback(Object bean, String sqlDialect) {
    this.bean = bean;
    this.sqlDialect = SqlDialect.normalize(sqlDialect);
  }

  @Override
  public void doWith(Field field) throws IllegalAccessException {
    if (!field.isAnnotationPresent(SqlStatement.class)) {
      return;
    }

    ReflectionUtils.makeAccessible(field);

    SqlStatement annotation = field.getDeclaredAnnotation(SqlStatement.class);
    String value = annotation.value();
    String namespace = annotation.nameSpace();

    if (StringUtils.isBlank(value)) {
      throw new SqlStatementException("The file name .sql must be defined");
    }

    String relativePath = buildRelativePath(namespace, value);
    String fileName = resolveExistingResource(relativePath);

    try (InputStream inputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
      if (inputStream == null) {
        throw new SqlStatementException("file not found [" + fileName + "]");
      }

      field.set(bean, IOUtils.toString(inputStream, StandardCharsets.UTF_8.name()));
    } catch (IOException exception) {
      throw new SqlStatementException("file not found [" + fileName + "]");
    }
  }

  private String resolveExistingResource(String relativePath) {
    List<String> candidates = new ArrayList<>();
    candidates.add("sql/" + sqlDialect + SLASH + relativePath);
    if (!SqlDialect.COMMON.equals(sqlDialect)) {
      candidates.add("sql/" + SqlDialect.COMMON + SLASH + relativePath);
    }
    candidates.add("sql/" + relativePath);

    for (String candidate : candidates) {
      if (resourceExists(candidate)) {
        return candidate;
      }
    }

    throw new SqlStatementException(
        "SQL file not found for dialect="
            + sqlDialect
            + " relativePath="
            + relativePath
            + " candidates="
            + candidates);
  }

  private boolean resourceExists(String resourcePath) {
    InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
    if (inputStream == null) {
      return false;
    }
    try {
      inputStream.close();
    } catch (IOException ignored) {
      // resource was found; closing is best-effort
    }
    return true;
  }

  private String buildRelativePath(String namespace, String value) {
    String fileName = value.contains(EXTENSION_SQL) ? value : value + EXTENSION_SQL;

    if (namespace != null && !namespace.trim().isEmpty()) {
      return namespace.replace(".", SLASH) + SLASH + fileName;
    }

    return fileName;
  }
}
