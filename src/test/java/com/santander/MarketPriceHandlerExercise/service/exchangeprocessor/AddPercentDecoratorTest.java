package com.santander.MarketPriceHandlerExercise.service.exchangeprocessor;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddPercentDecoratorTest {
    //    private ExchangeRateProcessor mockExchangeRateProcessor;
    private AddPercentDecorator addPercentDecorator;

    @BeforeEach
    void setUp() {
        addPercentDecorator = new AddPercentDecorator(new SimpleDecorator(), BigDecimal.TEN, BigDecimal.valueOf(20));
    }

    @Test
    void testProcess() {
        // Arrange
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setAsk(BigDecimal.valueOf(100));
        exchangeRate.setBid(BigDecimal.valueOf(200));

        ExchangeRate expectedExchangeRate = new ExchangeRate();
        expectedExchangeRate.setAsk(BigDecimal.valueOf(100 + 100 * 20 / 100));
        expectedExchangeRate.setBid(BigDecimal.valueOf(200 - 200 * 10 / 100));

        // Act
        ExchangeRate result = addPercentDecorator.process(exchangeRate);

        // Assert
        assertEquals(expectedExchangeRate, result);
    }
}