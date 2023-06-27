package com.santander.MarketPriceHandlerExercise;

import com.santander.MarketPriceHandlerExercise.messaging.CsvFeedListener;
import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MarketPriceHandlerExerciseApplicationIntegrationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public static void imitateIncomeMessageWithCsvData(ApplicationContext context) {
        String message = """
                106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
                107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
                108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
                """;

        CsvFeedListener csvFeedListener = context.getBean(CsvFeedListener.class);
        csvFeedListener.onMessage(message);
    }

    @Test
    void getAll_test() {

        ResponseEntity<ExchangeRate[]> responseEntity = restTemplate.getForEntity(createURLWithPort("/exchange-data/all"), ExchangeRate[].class);
        ExchangeRate[] responseExchangeRates = responseEntity.getBody();

        System.out.println(Arrays.toString(responseExchangeRates));

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseExchangeRates);
        assertEquals(3, responseExchangeRates.length);
        ExchangeRate eurUsd = Arrays.stream(responseExchangeRates).filter(o -> o.getId() == 106).findFirst().get();
        assertEquals(new ExchangeRate(106,
                "EUR/USD",
                new BigDecimal("1.09890"),
                new BigDecimal("1.20120"),
                LocalDateTime.parse("01-06-2020 12:01:01:001", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS"))), eurUsd);
    }

    @Test
    void getByInstrument_test() {

        ResponseEntity<ExchangeRate> responseEntity = restTemplate.getForEntity(createURLWithPort("/exchange-data/EUR/USD"), ExchangeRate.class);
        ExchangeRate responseExchangeRate = responseEntity.getBody();

        System.out.println(responseExchangeRate);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new ExchangeRate(106,
                "EUR/USD",
                new BigDecimal("1.09890"),
                new BigDecimal("1.20120"),
                LocalDateTime.parse("01-06-2020 12:01:01:001", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS"))), responseExchangeRate);
    }

    @Test
    void getByInstrument_null_test() {

        ResponseEntity<ExchangeRate> responseEntity = restTemplate.getForEntity(createURLWithPort("/exchange-data/PLN/USD"), ExchangeRate.class);
        ExchangeRate responseExchangeRate = responseEntity.getBody();

        System.out.println(responseExchangeRate);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseExchangeRate);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
