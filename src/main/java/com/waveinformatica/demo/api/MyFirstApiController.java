package com.waveinformatica.demo.api;

import com.waveinformatica.demo.dto.ListOfItems;
import com.waveinformatica.demo.dto.MarketDTO;
import com.waveinformatica.demo.services.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@Slf4j
public class MyFirstApiController {

    @Autowired
    private MarketService marketService;

    @GetMapping("/markets")
    public ListOfItems<MarketDTO> findMarkets(@RequestParam(value = "prefix", required = false) String prefix) {
        final Collection<MarketDTO> filteredMarkets = prefix == null
            ? marketService.listMarkets()
            : marketService.findMarkets(prefix);

        return new ListOfItems<>(filteredMarkets);
    }

    @GetMapping("/markets/{id}")
    public MarketDTO getMarket(@PathVariable long id) {
        MarketDTO result = marketService.getMarket(id);
        if (result == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Market '%d' not found", id));
        }
        return result;
    }

    @PostMapping("/markets")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMarket(@RequestBody final MarketDTO m) {
        log.info("Got market {}", m);
        if (!marketService.addMarket(m)) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @DeleteMapping("/markets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarket(@PathVariable long id) {
        if (!marketService.deleteMarket(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Market '%d' not found", id));
        }
    }

    @RequestMapping(value = "/markets/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMarket(@PathVariable long id, @RequestBody final MarketDTO m) {
        if (m.getId() != 0 && m.getId() != id) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Market id '%d' does not match with '%d'", m.getId(), id));
        }

        m.setId(id);
        if (!marketService.updateMarket(m)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Market '%d' not found", id));
        }
    }
}
