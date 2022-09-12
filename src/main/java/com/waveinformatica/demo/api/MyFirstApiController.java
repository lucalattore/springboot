package com.waveinformatica.demo.api;

import com.waveinformatica.demo.services.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
public class MyFirstApiController {

    @Autowired
    private MarketService marketService;

    @GetMapping("/markets")
    public String findMarkets(@RequestParam(value = "prefix", required = false) String prefix) {
        final Collection<String> markets = marketService.listMarkets();

        final List<String> filteredMarkets = new ArrayList<>();
        for (String m : markets) {
            if (prefix == null || m.startsWith(prefix)) {
                filteredMarkets.add(m);
            }
        }

        return filteredMarkets.toString();
    }

    @PostMapping("/markets")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMarket(@RequestBody final String s) {
        log.info("Got market {}", s);
        if (!marketService.addMarket(s)) {
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE);
        }
    }

    @DeleteMapping("/markets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarket(@PathVariable String id) {
        if (!marketService.deleteMarket(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Market '%s' not found", id));
        }
    }
}
