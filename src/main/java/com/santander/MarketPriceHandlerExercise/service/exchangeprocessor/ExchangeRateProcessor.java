package com.santander.MarketPriceHandlerExercise.service.exchangeprocessor;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;

public interface ExchangeRateProcessor {
    ExchangeRate process(ExchangeRate exchangeRate);
}
