package com.waveinformatica.demo.api;

import com.waveinformatica.demo.dto.ListOfItems;
import com.waveinformatica.demo.dto.MarketDTO;
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
    public ListOfItems<MarketDTO> findMarkets(@RequestParam(value = "prefix", required = false) String prefix) {
        final Collection<MarketDTO> markets = marketService.listMarkets();

        final List<MarketDTO> filteredMarkets = new ArrayList<>();
        for (MarketDTO m : markets) {
            if (prefix == null || (m.getName() != null && m.getName().startsWith(prefix))) {
                filteredMarkets.add(m);
            }
        }

        return new ListOfItems<>(filteredMarkets);
    }

    @GetMapping("/markets/{id}")
    public MarketDTO getMarket(@PathVariable long id) {
        MarketDTO result = marketService.getMarket(id);
        if (result == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Market '%s' not found", id));
        }
        return result;
    }

    @PostMapping("/markets")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMarket(@RequestBody final MarketDTO m) {
        log.info("Got market {}", m);
        if (!marketService.addMarket(m)) {
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE);
        }
    }

    @DeleteMapping("/markets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarket(@PathVariable long id) {
        if (!marketService.deleteMarket(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Market '%s' not found", id));
        }
    }
}
