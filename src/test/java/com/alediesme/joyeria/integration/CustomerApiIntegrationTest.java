package com.alediesme.joyeria.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getCustomerById() throws Exception {
        String token = obtainAccessToken();

        mockMvc.perform(get("/customer/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Maria")))
                .andExpect(jsonPath("$.lastName", is("Gomez")))
                .andExpect(jsonPath("$.documentNumber", is("1234567890")));
    }

    @Test
    void registerCustomer() throws Exception {
        String token = obtainAccessToken();

        mockMvc.perform(post("/customer")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "documentTypeId": 1,
                                  "documentNumber": "5555555555",
                                  "firstName": "Carlos",
                                  "lastName": "Ruiz",
                                  "email": "carlos.ruiz@example.com",
                                  "mobilePhone": "3005555555",
                                  "acceptsMarketing": false
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.documentNumber", is("5555555555")))
                .andExpect(jsonPath("$.firstName", is("Carlos")))
                .andExpect(jsonPath("$.enabled", is(true)));
    }
}
