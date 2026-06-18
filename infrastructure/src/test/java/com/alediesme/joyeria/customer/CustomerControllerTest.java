package com.alediesme.joyeria.customer;

import com.alediesme.joyeria.customer.adapter.in.web.CustomerController;
import com.alediesme.joyeria.customer.adapter.in.web.CustomerWebMapper;
import com.alediesme.joyeria.customer.dto.CustomerResponse;
import com.alediesme.joyeria.customer.dto.FindCustomerQuery;
import com.alediesme.joyeria.customer.dto.RegisterCustomerCommand;
import com.alediesme.joyeria.customer.port.in.FindCustomerUseCase;
import com.alediesme.joyeria.customer.port.in.RegisterCustomerUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private FindCustomerUseCase findCustomerUseCase;

    @Mock
    private RegisterCustomerUseCase registerCustomerUseCase;

    private CustomerController customerController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        customerController =
            new CustomerController(
                findCustomerUseCase, registerCustomerUseCase, new CustomerWebMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getCustomerById() throws Exception {
        when(findCustomerUseCase.execute(any(FindCustomerQuery.class)))
                .thenReturn(sampleResponse(1L));

        mockMvc.perform(get("/customer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Maria")))
                .andExpect(jsonPath("$.documentNumber", is("1234567890")));
    }

    @Test
    void registerCustomer() throws Exception {
        when(registerCustomerUseCase.execute(any(RegisterCustomerCommand.class)))
                .thenReturn(sampleResponse(2L));

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "documentTypeId": 1,
                                  "documentNumber": "9876543210",
                                  "firstName": "Carlos",
                                  "lastName": "Ruiz",
                                  "email": "carlos.ruiz@example.com",
                                  "mobilePhone": "3009876543",
                                  "acceptsMarketing": true
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("Maria")));
    }

    private CustomerResponse sampleResponse(Long id) {
        return new CustomerResponse(
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
                LocalDate.of(1990, 5, 15),
                LocalDate.of(2015, 6, 20),
                null,
                "Calle 10 # 20-30",
                null,
                "050001",
                null,
                true,
                "Prefiere oro 18K",
                true);
    }
}
