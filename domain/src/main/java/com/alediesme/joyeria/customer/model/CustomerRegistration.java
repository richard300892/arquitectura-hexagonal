package com.alediesme.joyeria.customer.model;

import com.alediesme.joyeria.shared.exception.InvalidArgumentException;

import java.time.LocalDate;

public final class CustomerRegistration {

  private final Long documentTypeId;
  private final String documentNumber;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final String phone;
  private final String mobilePhone;
  private final Long genderId;
  private final Long maritalStatusId;
  private final LocalDate birthDate;
  private final LocalDate anniversaryDate;
  private final Long neighborhoodId;
  private final String addressLine;
  private final String addressComplement;
  private final String postalCode;
  private final Long preferredCurrencyId;
  private final boolean acceptsMarketing;
  private final String notes;

  private CustomerRegistration(
      Long documentTypeId,
      String documentNumber,
      String firstName,
      String lastName,
      String email,
      String phone,
      String mobilePhone,
      Long genderId,
      Long maritalStatusId,
      LocalDate birthDate,
      LocalDate anniversaryDate,
      Long neighborhoodId,
      String addressLine,
      String addressComplement,
      String postalCode,
      Long preferredCurrencyId,
      boolean acceptsMarketing,
      String notes) {
    this.documentTypeId = documentTypeId;
    this.documentNumber = documentNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.mobilePhone = mobilePhone;
    this.genderId = genderId;
    this.maritalStatusId = maritalStatusId;
    this.birthDate = birthDate;
    this.anniversaryDate = anniversaryDate;
    this.neighborhoodId = neighborhoodId;
    this.addressLine = addressLine;
    this.addressComplement = addressComplement;
    this.postalCode = postalCode;
    this.preferredCurrencyId = preferredCurrencyId;
    this.acceptsMarketing = acceptsMarketing;
    this.notes = notes;
  }

  public static CustomerRegistration of(
      Long documentTypeId,
      String documentNumber,
      String firstName,
      String lastName,
      String email,
      String phone,
      String mobilePhone,
      Long genderId,
      Long maritalStatusId,
      LocalDate birthDate,
      LocalDate anniversaryDate,
      Long neighborhoodId,
      String addressLine,
      String addressComplement,
      String postalCode,
      Long preferredCurrencyId,
      boolean acceptsMarketing,
      String notes) {
    if (documentTypeId == null) {
      throw new InvalidArgumentException("Document type id is required");
    }
    if (documentNumber == null || documentNumber.isBlank()) {
      throw new InvalidArgumentException("Document number is required");
    }
    if (firstName == null || firstName.isBlank()) {
      throw new InvalidArgumentException("First name is required");
    }
    if (lastName == null || lastName.isBlank()) {
      throw new InvalidArgumentException("Last name is required");
    }
    return new CustomerRegistration(
        documentTypeId,
        documentNumber.trim(),
        firstName.trim(),
        lastName.trim(),
        normalizeOptional(email),
        normalizeOptional(phone),
        normalizeOptional(mobilePhone),
        genderId,
        maritalStatusId,
        birthDate,
        anniversaryDate,
        neighborhoodId,
        normalizeOptional(addressLine),
        normalizeOptional(addressComplement),
        normalizeOptional(postalCode),
        preferredCurrencyId,
        acceptsMarketing,
        normalizeOptional(notes));
  }

  private static String normalizeOptional(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    return value.trim();
  }

  public Long getDocumentTypeId() {
    return documentTypeId;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public Long getGenderId() {
    return genderId;
  }

  public Long getMaritalStatusId() {
    return maritalStatusId;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public LocalDate getAnniversaryDate() {
    return anniversaryDate;
  }

  public Long getNeighborhoodId() {
    return neighborhoodId;
  }

  public String getAddressLine() {
    return addressLine;
  }

  public String getAddressComplement() {
    return addressComplement;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public Long getPreferredCurrencyId() {
    return preferredCurrencyId;
  }

  public boolean isAcceptsMarketing() {
    return acceptsMarketing;
  }

  public String getNotes() {
    return notes;
  }
}
