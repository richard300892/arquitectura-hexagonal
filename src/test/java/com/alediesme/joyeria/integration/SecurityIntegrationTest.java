package com.alediesme.joyeria.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecurityIntegrationTest extends AbstractIntegrationTest {

    @Test
    void protectedEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/customer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is("AUTH_INVALID_CREDENTIALS")));
    }

    @Test
    void protectedEndpointAllowsValidJwt() throws Exception {
        String token = obtainAccessToken();

        mockMvc.perform(get("/customer/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Maria")));
    }

    @Test
    void healthEndpointIsPublic() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    void invalidJwtReturns401() throws Exception {
        mockMvc.perform(get("/customer/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer invalid-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is("AUTH_INVALID_TOKEN")));
    }

    @Test
    void responsesIncludeRequestCorrelationHeader() throws Exception {
        mockMvc.perform(get("/actuator/health")
                        .header("X-Request-Id", "integration-trace-001"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Request-Id", "integration-trace-001"));
    }

    @Test
    void webUserCanReadCustomerButCannotRegister() throws Exception {
        String webUserToken = obtainAccessToken("webuser", "admin123");

        mockMvc.perform(get("/customer/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + webUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/customer")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + webUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "documentTypeId": 1,
                                  "documentNumber": "5555555555",
                                  "firstName": "Pedro",
                                  "lastName": "Ruiz"
                                }
                                """))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is("AUTH_ACCESS_DENIED")));
    }
}
