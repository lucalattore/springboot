package com.waveinformatica.demo.services;

import com.waveinformatica.demo.dto.MarketDTO;

import java.util.Collection;

public interface MarketService {
    Collection<MarketDTO> listMarkets();
    Collection<MarketDTO> findMarkets(String prefix);
    MarketDTO getMarket(long id);
    boolean addMarket(MarketDTO m);
    boolean deleteMarket(long id);
}
