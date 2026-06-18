package com.alediesme.joyeria.customer.service;

import com.alediesme.joyeria.customer.exception.CustomerAlreadyExistsException;
import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.model.CustomerRegistration;
import com.alediesme.joyeria.customer.port.out.CustomerRepository;
import com.alediesme.joyeria.shared.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerDomainServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerDomainService customerDomainService;

    @BeforeEach
    void setUp() {
        customerDomainService = new CustomerDomainService(customerRepository);
    }

    @Test
    void findByIdReturnsCustomer() {
        Customer customer = sampleCustomer(1L);
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        Customer result = customerDomainService.findById(1L);

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Maria", result.getFirstName());
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> customerDomainService.findById(99L));
    }

    @Test
    void registerRejectsDuplicateDocument() {
        CustomerRegistration registration = sampleRegistration();
        when(customerRepository.existsByDocument(1L, "1234567890")).thenReturn(true);

        Assertions.assertThrows(CustomerAlreadyExistsException.class, () ->
                customerDomainService.register(registration));

        verify(customerRepository, never()).save(any());
    }

    @Test
    void registerRejectsDuplicateEmail() {
        CustomerRegistration registration = sampleRegistration();
        when(customerRepository.existsByDocument(1L, "1234567890")).thenReturn(false);
        when(customerRepository.existsByEmail("maria.gomez@example.com")).thenReturn(true);

        Assertions.assertThrows(CustomerAlreadyExistsException.class, () ->
                customerDomainService.register(registration));
    }

    @Test
    void registerPersistsCustomer() {
        CustomerRegistration registration = sampleRegistration();
        Customer saved = sampleCustomer(2L);

        when(customerRepository.existsByDocument(1L, "1234567890")).thenReturn(false);
        when(customerRepository.existsByEmail("maria.gomez@example.com")).thenReturn(false);
        when(customerRepository.save(registration)).thenReturn(saved);

        Customer result = customerDomainService.register(registration);

        Assertions.assertEquals(2L, result.getId());
        verify(customerRepository).save(registration);
    }

    private CustomerRegistration sampleRegistration() {
        return CustomerRegistration.of(
                1L,
                "1234567890",
                "Maria",
                "Gomez",
                "maria.gomez@example.com",
                "6041234567",
                "3001234567",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true,
                "Prefiere oro 18K");
    }

    private Customer sampleCustomer(Long id) {
        return Customer.restore(
                id,
                null,
                1L,
                "1234567890",
                "Maria",
                "Gomez",
                "maria.gomez@example.com",
                "6041234567",
                "3001234567",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true,
                "Prefiere oro 18K",
                true);
    }
}
