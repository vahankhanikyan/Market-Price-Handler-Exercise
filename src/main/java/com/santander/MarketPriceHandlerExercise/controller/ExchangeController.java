package com.santander.MarketPriceHandlerExercise.controller;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import com.santander.MarketPriceHandlerExercise.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/exchange-data")
public class ExchangeController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/all")
    public Collection<ExchangeRate> getAll() {
        return exchangeRateService.getAll();
    }

    @GetMapping("/{instrumentFrom}/{instrumentTo}")
    public Optional<ExchangeRate> getByInstrument(@PathVariable String instrumentFrom, @PathVariable String instrumentTo){
        return exchangeRateService.getByInstrument(instrumentFrom + "/" + instrumentTo);
    }
}
