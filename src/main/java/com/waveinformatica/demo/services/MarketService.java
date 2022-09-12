package com.waveinformatica.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MarketService {

    @Value("${demo.rest.markets.limit:-1}")
    private int capacity;

    private BlockingQueue<String> markets;

    @PostConstruct
    private void init() {
        this.markets = new LinkedBlockingQueue<>(capacity <= 0 ? Integer.MAX_VALUE : capacity);
    }

    public Collection<String> listMarkets() {
        return markets;
    }

    public boolean addMarket(String m) {
        return this.markets.offer(m);
    }

    public boolean deleteMarket(String m) {
        return this.markets.remove(m);
    }
}
