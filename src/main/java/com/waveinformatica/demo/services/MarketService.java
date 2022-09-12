package com.waveinformatica.demo.services;

import com.waveinformatica.demo.dto.MarketDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MarketService {

    @Value("${demo.rest.markets.limit:-1}")
    private int capacity;

    private ConcurrentHashMap<Long, MarketDTO> marketMap;

    @PostConstruct
    private void init() {
        this.marketMap = new ConcurrentHashMap<>(capacity <= 0 ? Integer.MAX_VALUE : capacity);
    }

    public Collection<MarketDTO> listMarkets() {
        return marketMap.values();
    }

    public MarketDTO getMarket(long id) {
        return marketMap.get(id);
    }

    public boolean addMarket(MarketDTO m) {
        this.marketMap.put(m.getId(), m);
        return true;
    }

    public boolean deleteMarket(long m) {
        return this.marketMap.remove(m) != null;
    }
}
