package com.alediesme.joyeria.shared.persistence.jdbc;

@FunctionalInterface
public interface ExecutionCallback<T> {

  T execute();
}
