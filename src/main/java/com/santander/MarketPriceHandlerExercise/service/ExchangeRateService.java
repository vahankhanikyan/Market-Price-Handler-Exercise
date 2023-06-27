package com.santander.MarketPriceHandlerExercise.service;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;

import java.util.Collection;
import java.util.Optional;

public interface ExchangeRateService {
    void save(Collection<ExchangeRate> exchangeRates);

    Collection<ExchangeRate> getAll();

    Optional<ExchangeRate> getByInstrument(String instrument);
}
