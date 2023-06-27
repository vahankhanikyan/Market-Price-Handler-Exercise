package com.santander.MarketPriceHandlerExercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {
    private long id;
    /**
     * currency from/to.
     * example: EUR/USD or EUR/JPY or GBP/USD
     */
    private String instrumentName;
    private BigDecimal bid;
    private BigDecimal ask;
    private LocalDateTime timestamp;
}
