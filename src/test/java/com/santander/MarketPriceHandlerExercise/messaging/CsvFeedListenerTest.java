package com.santander.MarketPriceHandlerExercise.messaging;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CsvFeedListenerTest {
    @Autowired
    private CsvFeedListener csvFeedListener;

    @Test
    public void testParseCSV() {
        String message = """
                106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
                107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
                108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
                """;

        List<ExchangeRate> exchangeRates = csvFeedListener.parseCSV(message);

        assertEquals(3, exchangeRates.size());

        ExchangeRate rate1 = exchangeRates.get(0);
        assertAll("rate1",
                () -> assertEquals(106L, rate1.getId()),
                () -> assertEquals("EUR/USD", rate1.getInstrumentName()),
                () -> assertEquals(new BigDecimal("1.1000"), rate1.getBid()),
                () -> assertEquals(new BigDecimal("1.2000"), rate1.getAsk()),
                () -> assertEquals(LocalDateTime.parse("01-06-2020 12:01:01:001", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS")), rate1.getTimestamp())
        );


        ExchangeRate rate2 = exchangeRates.get(1);
        assertAll("rate2",
                () -> assertEquals(107L, rate2.getId()),
                () -> assertEquals("EUR/JPY", rate2.getInstrumentName()),
                () -> assertEquals(new BigDecimal("119.60"), rate2.getBid()),
                () -> assertEquals(new BigDecimal("119.90"), rate2.getAsk()),
                () -> assertEquals(LocalDateTime.parse("01-06-2020 12:01:02:002", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS")), rate2.getTimestamp())
        );


        ExchangeRate rate3 = exchangeRates.get(2);
        assertAll("rate3",
                () -> assertEquals(108L, rate3.getId()),
                () -> assertEquals("GBP/USD", rate3.getInstrumentName()),
                () -> assertEquals(new BigDecimal("1.2500"), rate3.getBid()),
                () -> assertEquals(new BigDecimal("1.2560"), rate3.getAsk()),
                () -> assertEquals(LocalDateTime.parse("01-06-2020 12:01:02:002", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS")), rate3.getTimestamp())
        );

    }
}