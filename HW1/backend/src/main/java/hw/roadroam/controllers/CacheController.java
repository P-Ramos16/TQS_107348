package hw.roadroam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import hw.roadroam.services.CurrencyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CurrencyService currencyService;

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    @GetMapping(path = "/getHits",  produces = "application/json")
    public Integer getCacheHits() {
        logger.info("Cache hits returned!");
        return currencyService.getCacheHits();
    }

    @GetMapping(path = "/getMisses",  produces = "application/json")
    public Integer getCacheMisses() {
        logger.info("Cache misses returned!");
        return currencyService.getCacheMisses();
    }
}
