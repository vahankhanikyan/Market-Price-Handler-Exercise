package com.santander.MarketPriceHandlerExercise.service;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import com.santander.MarketPriceHandlerExercise.repository.ExchangeRateRepository;
import com.santander.MarketPriceHandlerExercise.service.exchangeprocessor.AddPercentDecorator;
import com.santander.MarketPriceHandlerExercise.service.exchangeprocessor.SimpleDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Value("${bid.commission.margin}")
    private BigDecimal bidMargin;

    @Value("${ask.commission.margin}")
    private BigDecimal askMargin;

    @Override
    public void save(Collection<ExchangeRate> exchangeRates) {
        Collection<ExchangeRate> exchangeRatesToSave = exchangeRates.stream()
                .map(o -> new AddPercentDecorator(new SimpleDecorator(), bidMargin, askMargin).process(o))
                .collect(Collectors.toCollection(LinkedList::new));
        exchangeRateRepository.save(exchangeRatesToSave);
    }

    @Override
    public Collection<ExchangeRate> getAll() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public Optional<ExchangeRate> getByInstrument(String instrument) {
        return exchangeRateRepository.findByInstrument(instrument);
    }


}
