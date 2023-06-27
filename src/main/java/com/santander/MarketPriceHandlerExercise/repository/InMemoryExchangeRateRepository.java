package com.santander.MarketPriceHandlerExercise.repository;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryExchangeRateRepository implements ExchangeRateRepository {
    private final ConcurrentMap<String, ExchangeRate> instrumentToExchangeRate = new ConcurrentHashMap<>();

    @Override
    public void save(@NonNull Collection<ExchangeRate> exchangeRates) {
        for (ExchangeRate exchangeRate : exchangeRates) {
            // update value if there is more fresh version of exchange
            instrumentToExchangeRate.computeIfPresent(exchangeRate.getInstrumentName(), (key, value) -> (value.getTimestamp().isBefore(exchangeRate.getTimestamp())) ? exchangeRate : value);
            instrumentToExchangeRate.putIfAbsent(exchangeRate.getInstrumentName(), exchangeRate);
        }
    }

    @Override
    public Collection<ExchangeRate> findAll() {
        return instrumentToExchangeRate.values();
    }

    @Override
    public Optional<ExchangeRate> findByInstrument(String instrumentName) {
        return Optional.ofNullable(instrumentToExchangeRate.get(instrumentName));
    }
}
