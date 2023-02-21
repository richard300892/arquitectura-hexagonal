package com.asociados.cope.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;

public class DatabaseExecution {
    public static <T> T getObjectOrNull(ExecutionDB<T> executionDB) {
        try {
            return executionDB.execution();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}