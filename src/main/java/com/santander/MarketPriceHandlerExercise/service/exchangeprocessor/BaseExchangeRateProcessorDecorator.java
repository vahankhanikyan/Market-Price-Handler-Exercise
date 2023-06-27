package com.santander.MarketPriceHandlerExercise.service.exchangeprocessor;

abstract class BaseExchangeRateProcessorDecorator implements ExchangeRateProcessor {
    protected final ExchangeRateProcessor exchangeRateProcessor;

    public BaseExchangeRateProcessorDecorator(ExchangeRateProcessor exchangeRateProcessor) {
        this.exchangeRateProcessor = exchangeRateProcessor;
    }
}
