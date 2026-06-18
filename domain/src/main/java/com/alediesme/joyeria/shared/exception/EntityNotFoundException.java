package com.alediesme.joyeria.shared.exception;

public class EntityNotFoundException extends DomainException {

  public EntityNotFoundException(String entityName, Long id) {
    super(ErrorCode.NOT_FOUND, entityName + " not found with id " + id);
  }

  public EntityNotFoundException(String message) {
    super(ErrorCode.NOT_FOUND, message);
  }
}
