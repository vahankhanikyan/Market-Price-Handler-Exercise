package com.santander.MarketPriceHandlerExercise.service.exchangeprocessor;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;

public class SimpleDecorator implements ExchangeRateProcessor {
    @Override
    public ExchangeRate process(ExchangeRate exchangeRate) {
        return exchangeRate;
    }
}
