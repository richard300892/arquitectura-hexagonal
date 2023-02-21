package com.asociados.cope.price.gateways.dao;

import com.asociados.cope.price.model.dao.SummaryPriceRequestDTO;
import com.asociados.cope.price.model.dao.SummaryPriceResponseDTO;

public interface DaoPrice {
    SummaryPriceResponseDTO getPriceByIdBrandAndIdProductAndDateApplication(SummaryPriceRequestDTO summaryPriceRequestDTO);
}