package com.santander.MarketPriceHandlerExercise.repository;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;

import java.util.Collection;
import java.util.Optional;

public interface ExchangeRateRepository {
    void save(Collection<ExchangeRate> exchangeRates);

    Collection<ExchangeRate> findAll();

    Optional<ExchangeRate> findByInstrument(String instrumentName);
}
