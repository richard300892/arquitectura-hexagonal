package com.alediesme.joyeria.customer.adapter.in.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class RegisterCustomerRequest {

  @NotNull(message = "documentTypeId is required")
  private Long documentTypeId;

  @NotBlank(message = "documentNumber is required")
  @Size(max = 30, message = "documentNumber must be at most 30 characters")
  private String documentNumber;

  @NotBlank(message = "firstName is required")
  @Size(max = 100, message = "firstName must be at most 100 characters")
  private String firstName;

  @NotBlank(message = "lastName is required")
  @Size(max = 100, message = "lastName must be at most 100 characters")
  private String lastName;

  @Size(max = 150, message = "email must be at most 150 characters")
  private String email;

  @Size(max = 30, message = "phone must be at most 30 characters")
  private String phone;

  @Size(max = 30, message = "mobilePhone must be at most 30 characters")
  private String mobilePhone;

  private Long genderId;
  private Long maritalStatusId;
  private LocalDate birthDate;
  private LocalDate anniversaryDate;
  private Long neighborhoodId;

  @Size(max = 200, message = "addressLine must be at most 200 characters")
  private String addressLine;

  @Size(max = 100, message = "addressComplement must be at most 100 characters")
  private String addressComplement;

  @Size(max = 20, message = "postalCode must be at most 20 characters")
  private String postalCode;

  private Long preferredCurrencyId;
  private boolean acceptsMarketing;

  @Size(max = 500, message = "notes must be at most 500 characters")
  private String notes;

  public Long getDocumentTypeId() {
    return documentTypeId;
  }

  public void setDocumentTypeId(Long documentTypeId) {
    this.documentTypeId = documentTypeId;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public Long getGenderId() {
    return genderId;
  }

  public void setGenderId(Long genderId) {
    this.genderId = genderId;
  }

  public Long getMaritalStatusId() {
    return maritalStatusId;
  }

  public void setMaritalStatusId(Long maritalStatusId) {
    this.maritalStatusId = maritalStatusId;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public LocalDate getAnniversaryDate() {
    return anniversaryDate;
  }

  public void setAnniversaryDate(LocalDate anniversaryDate) {
    this.anniversaryDate = anniversaryDate;
  }

  public Long getNeighborhoodId() {
    return neighborhoodId;
  }

  public void setNeighborhoodId(Long neighborhoodId) {
    this.neighborhoodId = neighborhoodId;
  }

  public String getAddressLine() {
    return addressLine;
  }

  public void setAddressLine(String addressLine) {
    this.addressLine = addressLine;
  }

  public String getAddressComplement() {
    return addressComplement;
  }

  public void setAddressComplement(String addressComplement) {
    this.addressComplement = addressComplement;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public Long getPreferredCurrencyId() {
    return preferredCurrencyId;
  }

  public void setPreferredCurrencyId(Long preferredCurrencyId) {
    this.preferredCurrencyId = preferredCurrencyId;
  }

  public boolean isAcceptsMarketing() {
    return acceptsMarketing;
  }

  public void setAcceptsMarketing(boolean acceptsMarketing) {
    this.acceptsMarketing = acceptsMarketing;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
