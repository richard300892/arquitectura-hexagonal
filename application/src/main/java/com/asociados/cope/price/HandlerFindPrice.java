package com.asociados.cope.price;

import com.asociados.cope.price.gateways.dao.DaoPrice;
import com.asociados.cope.price.model.dao.SummaryPriceRequestDTO;
import com.asociados.cope.price.model.dao.SummaryPriceResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HandlerFindPrice {
    private final DaoPrice daoPrice;

    public HandlerFindPrice(DaoPrice daoPrice) {
        this.daoPrice = daoPrice;
    }

    public SummaryPriceResponseDTO execute(Long idBrand, Long idProduct, String dataApplication) {
        LocalDateTime localDateTime = LocalDateTime.parse(dataApplication);

        SummaryPriceRequestDTO summaryPriceRequestDTO =
                new SummaryPriceRequestDTO(idBrand, idProduct, localDateTime);

        return daoPrice.getPriceByIdBrandAndIdProductAndDateApplication(summaryPriceRequestDTO);
    }
}