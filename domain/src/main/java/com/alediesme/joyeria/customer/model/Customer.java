package com.alediesme.joyeria.customer.model;

import com.alediesme.joyeria.customer.valueobject.CustomerId;

import java.time.LocalDate;

public final class Customer {

  private final CustomerId id;
  private final Long appUserId;
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
  private final boolean enabled;

  private Customer(
      CustomerId id,
      Long appUserId,
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
      String notes,
      boolean enabled) {
    this.id = id;
    this.appUserId = appUserId;
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
    this.enabled = enabled;
  }

  public static Customer restore(
      Long id,
      Long appUserId,
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
      String notes,
      boolean enabled) {
    return new Customer(
        CustomerId.of(id),
        appUserId,
        documentTypeId,
        documentNumber,
        firstName,
        lastName,
        email,
        phone,
        mobilePhone,
        genderId,
        maritalStatusId,
        birthDate,
        anniversaryDate,
        neighborhoodId,
        addressLine,
        addressComplement,
        postalCode,
        preferredCurrencyId,
        acceptsMarketing,
        notes,
        enabled);
  }

  public Long getId() {
    return id.value();
  }

  public Long getAppUserId() {
    return appUserId;
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

  public boolean isEnabled() {
    return enabled;
  }
}
