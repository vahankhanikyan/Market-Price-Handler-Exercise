package com.santander.MarketPriceHandlerExercise.repository;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryExchangeRateRepositoryTest {

    private InMemoryExchangeRateRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryExchangeRateRepository();
    }

    @Test
    void save_ShouldSaveExchangeRates() {
        // Arrange
        ExchangeRate exchangeRate1 = new ExchangeRate(1, "USD/JPY", BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0), LocalDateTime.now());
        ExchangeRate exchangeRate2 = new ExchangeRate(2, "EUR/JPY", BigDecimal.valueOf(0.8), BigDecimal.valueOf(1.8), LocalDateTime.now());
        Collection<ExchangeRate> exchangeRates = Arrays.asList(exchangeRate1, exchangeRate2);

        // Act
        repository.save(exchangeRates);

        // Assert
        Collection<ExchangeRate> savedExchangeRates = repository.findAll();
        assertNotNull(savedExchangeRates);
        assertAll(
                () -> assertEquals(2, savedExchangeRates.size()),
                () -> assertTrue(savedExchangeRates.contains(exchangeRate1)),
                () -> assertTrue(savedExchangeRates.contains(exchangeRate2))
        );

    }

    @Test
    void save_ShouldUpdateExistingExchangeRate() {
        // Arrange
        ExchangeRate initialExchangeRate = new ExchangeRate(1, "USD/JPY", BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0), LocalDateTime.now());
        ExchangeRate updatedExchangeRate = new ExchangeRate(2, "USD/JPY", BigDecimal.valueOf(0.8), BigDecimal.valueOf(1.8), LocalDateTime.now().plus(10, ChronoUnit.SECONDS));
        Collection<ExchangeRate> exchangeRates = Arrays.asList(initialExchangeRate, updatedExchangeRate);

        // Act
        repository.save(exchangeRates);

        // Assert
        Collection<ExchangeRate> savedExchangeRates = repository.findAll();
        assertNotNull(savedExchangeRates);
        assertAll(
                () -> assertEquals(1, savedExchangeRates.size()),
                () -> assertEquals(updatedExchangeRate, savedExchangeRates.iterator().next())
        );

    }

    @Test
    void testFindAll() {
        // Arrange
        ExchangeRate exchangeRate1 = createExchangeRate("USD", 1.0);
        ExchangeRate exchangeRate2 = createExchangeRate("EUR", 2.0);
        repository.save(Arrays.asList(exchangeRate1, exchangeRate2));

        // Act
        Collection<ExchangeRate> result = repository.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(exchangeRate1));
        assertTrue(result.contains(exchangeRate2));
    }

    @Test
    void testFindByInstrument() {
        // Arrange
        ExchangeRate exchangeRate1 = createExchangeRate("USD", 1.0);
        ExchangeRate exchangeRate2 = createExchangeRate("EUR", 2.0);
        repository.save(Arrays.asList(exchangeRate1, exchangeRate2));

        // Act
        Optional<ExchangeRate> result = repository.findByInstrument("USD");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(exchangeRate1, result.get());
    }

    @Test
    void testFindByInstrumentWithNonExistingInstrument() {
        // Act
        Optional<ExchangeRate> result = repository.findByInstrument("USD");

        // Assert
        assertFalse(result.isPresent());
    }

    private ExchangeRate createExchangeRate(String instrumentName, double rate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setInstrumentName(instrumentName);
        exchangeRate.setBid(BigDecimal.valueOf(rate));
        exchangeRate.setAsk(BigDecimal.valueOf(rate));
        exchangeRate.setTimestamp(LocalDateTime.now());
        return exchangeRate;
    }
}