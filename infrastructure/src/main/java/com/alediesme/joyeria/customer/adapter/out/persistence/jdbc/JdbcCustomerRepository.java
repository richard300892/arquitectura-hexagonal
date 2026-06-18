package com.alediesme.joyeria.customer.adapter.out.persistence.jdbc;

import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.model.CustomerRegistration;
import com.alediesme.joyeria.customer.port.out.CustomerRepository;
import com.alediesme.joyeria.customer.valueobject.CustomerId;
import com.alediesme.joyeria.shared.persistence.jdbc.DatabaseExecution;
import com.alediesme.joyeria.shared.persistence.jdbc.JdbcTemplateProvider;
import com.alediesme.joyeria.shared.persistence.jdbc.sql.SqlStatement;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

  private final JdbcTemplateProvider jdbcTemplateProvider;

  @SqlStatement(nameSpace = "customer", value = "getById")
  private static String sqlFindById;

  @SqlStatement(nameSpace = "customer", value = "existsByDocument")
  private static String sqlExistsByDocument;

  @SqlStatement(nameSpace = "customer", value = "existsByEmail")
  private static String sqlExistsByEmail;

  @SqlStatement(nameSpace = "customer", value = "insert")
  private static String sqlInsert;

  public JdbcCustomerRepository(JdbcTemplateProvider jdbcTemplateProvider) {
    this.jdbcTemplateProvider = jdbcTemplateProvider;
  }

  @Override
  public Optional<Customer> findById(CustomerId id) {
    MapSqlParameterSource parameters = new MapSqlParameterSource("id", id.value());

    Customer customer =
        DatabaseExecution.getObjectOrNull(
            () ->
                jdbcTemplateProvider
                    .getTemplate()
                    .queryForObject(sqlFindById, parameters, new CustomerRowMapper()));

    return Optional.ofNullable(customer);
  }

  @Override
  public boolean existsByDocument(Long documentTypeId, String documentNumber) {
    MapSqlParameterSource parameters =
        new MapSqlParameterSource()
            .addValue("documentTypeId", documentTypeId)
            .addValue("documentNumber", documentNumber);

    Integer count =
        DatabaseExecution.execute(
            () ->
                jdbcTemplateProvider
                    .getTemplate()
                    .queryForObject(sqlExistsByDocument, parameters, Integer.class));

    return count != null && count > 0;
  }

  @Override
  public boolean existsByEmail(String email) {
    MapSqlParameterSource parameters = new MapSqlParameterSource("email", email);

    Integer count =
        DatabaseExecution.execute(
            () ->
                jdbcTemplateProvider
                    .getTemplate()
                    .queryForObject(sqlExistsByEmail, parameters, Integer.class));

    return count != null && count > 0;
  }

  @Override
  public Customer save(CustomerRegistration registration) {
    MapSqlParameterSource parameters = buildInsertParameters(registration);
    KeyHolder keyHolder = new GeneratedKeyHolder();

    DatabaseExecution.execute(
        () ->
            jdbcTemplateProvider
                .getTemplate()
                .update(sqlInsert, parameters, keyHolder, new String[] {"id"}));

    Number generatedId = keyHolder.getKey();
    if (generatedId == null) {
      throw new IllegalStateException("Customer id was not generated");
    }

    return findById(CustomerId.of(generatedId.longValue()))
        .orElseThrow(() -> new IllegalStateException("Customer was not found after insert"));
  }

  private MapSqlParameterSource buildInsertParameters(CustomerRegistration registration) {
    return new MapSqlParameterSource()
        .addValue("documentTypeId", registration.getDocumentTypeId())
        .addValue("documentNumber", registration.getDocumentNumber())
        .addValue("firstName", registration.getFirstName())
        .addValue("lastName", registration.getLastName())
        .addValue("email", registration.getEmail())
        .addValue("phone", registration.getPhone())
        .addValue("mobilePhone", registration.getMobilePhone())
        .addValue("genderId", registration.getGenderId())
        .addValue("maritalStatusId", registration.getMaritalStatusId())
        .addValue("birthDate", toSqlDate(registration.getBirthDate()))
        .addValue("anniversaryDate", toSqlDate(registration.getAnniversaryDate()))
        .addValue("neighborhoodId", registration.getNeighborhoodId())
        .addValue("addressLine", registration.getAddressLine())
        .addValue("addressComplement", registration.getAddressComplement())
        .addValue("postalCode", registration.getPostalCode())
        .addValue("preferredCurrencyId", registration.getPreferredCurrencyId())
        .addValue("acceptsMarketing", registration.isAcceptsMarketing() ? 1 : 0)
        .addValue("notes", registration.getNotes());
  }

  private Date toSqlDate(java.time.LocalDate localDate) {
    if (localDate == null) {
      return null;
    }
    return Date.valueOf(localDate);
  }
}
