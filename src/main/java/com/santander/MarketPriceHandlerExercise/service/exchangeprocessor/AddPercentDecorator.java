package com.santander.MarketPriceHandlerExercise.service.exchangeprocessor;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class AddPercentDecorator extends BaseExchangeRateProcessorDecorator {
    private final BigDecimal bidMargin;
    private final BigDecimal askMargin;

    public AddPercentDecorator(ExchangeRateProcessor exchangeRateProcessor, BigDecimal bidMargin, BigDecimal askMargin) {
        super(exchangeRateProcessor);
        this.bidMargin = bidMargin;
        this.askMargin = askMargin;
    }

    @Override
    public ExchangeRate process(ExchangeRate exchangeRate) {
        ExchangeRate exchangeRateCopy = new ExchangeRate();
        BeanUtils.copyProperties(exchangeRate, exchangeRateCopy);
        BigDecimal newAsk = exchangeRateCopy.getAsk().add(exchangeRateCopy.getAsk().multiply(askMargin).divide(BigDecimal.valueOf(100)));
        BigDecimal newBid = exchangeRateCopy.getBid().add(exchangeRateCopy.getBid().multiply(bidMargin).divide(BigDecimal.valueOf(-100)));
        exchangeRateCopy.setAsk(newAsk);
        exchangeRateCopy.setBid(newBid);
        return exchangeRateProcessor.process(exchangeRateCopy);
    }
}
