package com.asociados.cope.price;

import com.asociados.cope.ApplicationMock;
import com.asociados.cope.price.controller.ControllerPrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerPrice.class)
@ContextConfiguration(classes = ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ControllerPriceTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPriceByDay14Hour10ByIdBrandAndIdProduct() throws Exception {
        mockMvc.perform(get("/price/")
                        .param("idBrand", "1")
                        .param("idProduct", "1")
                        .param("dateApplication", "2022-02-14T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.priceList", is(10)))
                .andExpect(jsonPath("$.idBrand", is(1)))
                .andExpect(jsonPath("$.idProduct", is(1)))
                .andExpect(jsonPath("$.price", is(18000)));
    }

    @Test
    void getPriceByDay14Hour16ByIdBrandAndIdProduct() throws Exception {
        mockMvc.perform(get("/price/")
                        .param("idBrand", "1")
                        .param("idProduct", "1")
                        .param("dateApplication", "2022-02-14T16:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.priceList", is(20)))
                .andExpect(jsonPath("$.idBrand", is(1)))
                .andExpect(jsonPath("$.idProduct", is(1)))
                .andExpect(jsonPath("$.price", is(16000)));
    }

    @Test
    void getPriceByDay14Hour21ByIdBrandAndIdProduct() throws Exception {
        mockMvc.perform(get("/price/")
                        .param("idBrand", "1")
                        .param("idProduct", "1")
                        .param("dateApplication", "2022-02-14T21:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.priceList", is(30)))
                .andExpect(jsonPath("$.idBrand", is(1)))
                .andExpect(jsonPath("$.idProduct", is(1)))
                .andExpect(jsonPath("$.price", is(14000)));
    }

    @Test
    void getPriceByDay15Hour10ByIdBrandAndIdProduct() throws Exception {
        mockMvc.perform(get("/price/")
                        .param("idBrand", "1")
                        .param("idProduct", "1")
                        .param("dateApplication", "2022-02-15T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.priceList", is(40)))
                .andExpect(jsonPath("$.idBrand", is(1)))
                .andExpect(jsonPath("$.idProduct", is(1)))
                .andExpect(jsonPath("$.price", is(12000)));
    }

    @Test
    void getPriceByDay16Hour21ByIdBrandAndIdProduct() throws Exception {
        mockMvc.perform(get("/price/")
                        .param("idBrand", "1")
                        .param("idProduct", "1")
                        .param("dateApplication", "2022-02-16T21:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.priceList", is(50)))
                .andExpect(jsonPath("$.idBrand", is(1)))
                .andExpect(jsonPath("$.idProduct", is(1)))
                .andExpect(jsonPath("$.price", is(10000)));
    }
}