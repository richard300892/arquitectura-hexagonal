package com.alediesme.joyeria.customer.adapter.out.persistence.jdbc;

import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.model.CustomerRegistration;
import com.alediesme.joyeria.customer.valueobject.CustomerId;
import com.alediesme.joyeria.shared.persistence.jdbc.JdbcTemplateProvider;
import com.alediesme.joyeria.shared.persistence.jdbc.sql.SqlDialect;
import com.alediesme.joyeria.shared.persistence.jdbc.sql.SqlStatementFieldCallback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Optional;

class JdbcCustomerRepositoryTest {

  private JdbcCustomerRepository repository;

  @BeforeEach
  void setUp() {
    String jdbcUrl =
        "jdbc:h2:mem:customer-repo-"
            + System.nanoTime()
            + ";MODE=Oracle;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    DataSource dataSource = new DriverManagerDataSource(jdbcUrl, "sa", "");

    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("jdbc-test-schema.sql"));
    populator.execute(dataSource);

    JdbcTemplateProvider provider =
        new JdbcTemplateProvider(new NamedParameterJdbcTemplate(dataSource));
    repository = new JdbcCustomerRepository(provider);
    ReflectionUtils.doWithFields(
        JdbcCustomerRepository.class,
        new SqlStatementFieldCallback(repository, SqlDialect.COMMON));
  }

  @Test
  void saveAndFindById() {
    CustomerRegistration registration =
        CustomerRegistration.of(
            1L,
            "5555555555",
            "Carlos",
            "Ruiz",
            "carlos.ruiz@example.com",
            "6045555555",
            "3005555555",
            null,
            null,
            LocalDate.of(1988, 3, 10),
            null,
            null,
            "Calle 20 # 10-15",
            null,
            "050001",
            null,
            true,
            "Cliente VIP");

    Customer saved = repository.save(registration);

    Optional<Customer> found = repository.findById(CustomerId.of(saved.getId()));

    Assertions.assertTrue(found.isPresent());
    Assertions.assertEquals("5555555555", found.get().getDocumentNumber());
    Assertions.assertEquals("Carlos", found.get().getFirstName());
    Assertions.assertTrue(found.get().isEnabled());
  }

  @Test
  void existsByDocumentAndEmail() {
    CustomerRegistration registration =
        CustomerRegistration.of(
            1L,
            "7777777777",
            "Ana",
            "Lopez",
            "ana.lopez@example.com",
            null,
            "3007777777",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null);

    repository.save(registration);

    Assertions.assertTrue(repository.existsByDocument(1L, "7777777777"));
    Assertions.assertTrue(repository.existsByEmail("ana.lopez@example.com"));
    Assertions.assertFalse(repository.existsByDocument(1L, "0000000000"));
  }
}
