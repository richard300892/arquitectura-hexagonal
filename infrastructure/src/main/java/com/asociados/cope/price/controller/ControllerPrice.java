package com.asociados.cope.price.controller;

import com.asociados.cope.price.HandlerFindPrice;
import com.asociados.cope.price.model.dao.SummaryPriceResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/price")
@Tag(name = "Controller find price")
public class ControllerPrice {
    private final HandlerFindPrice handlerFindPrice;

    public ControllerPrice(HandlerFindPrice handlerFindPrice) {
        this.handlerFindPrice = handlerFindPrice;
    }

    @GetMapping("/")
    public SummaryPriceResponseDTO getPriceByIdBrandAndIdProductAndDateApplication(
            @RequestParam Long idBrand,
            @RequestParam Long idProduct,
            @RequestParam String dateApplication) {
        return handlerFindPrice.execute(idBrand, idProduct, dateApplication);
    }
}