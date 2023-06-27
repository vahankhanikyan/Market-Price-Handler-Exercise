package com.santander.MarketPriceHandlerExercise.messaging;

import com.santander.MarketPriceHandlerExercise.model.ExchangeRate;
import com.santander.MarketPriceHandlerExercise.service.ExchangeRateService;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The market price feed will be given to you in CSV format line by line for EUR/USD, GBP/USD and
 * EUR/JPY, e.g. here are some individual messages (each message could have 1 or more lines in it):
 * 106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
 * …
 * 107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
 * …
 * 108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
 * …
 * 109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100
 * …
 * 110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110
 */

@Component
@Log
public class CsvFeedListener implements FeedListener {
    @Value("${csv.feed.separator}")
    private String csvSeparator;

    @Value("${csv.feed.timestamp.format}")
    private String timestampFormat;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Override
    public void onMessage(@NonNull String message) {
        List<ExchangeRate> exchangeRates = parseCSV(message);
        exchangeRateService.save(exchangeRates);
    }

    List<ExchangeRate> parseCSV(String message) {
        return message
                .lines()
                .map((o) -> o.split(csvSeparator))
                // TODO if there is a need to do validation of CSV. it can be done here with throwing specific exception and logging. Now I assume that messages are valid
                // Possible to handle also partially valid CSV. Valid lines can be with invalid in one message. Need to clarify with requirements
                .map((o) -> {
                    trim(o);
                    var exchangeRate = new ExchangeRate();
                    exchangeRate.setId(Long.parseLong(o[0]));
                    exchangeRate.setInstrumentName(o[1]);
                    exchangeRate.setBid(new BigDecimal((o[2])));
                    exchangeRate.setAsk(new BigDecimal(o[3]));
                    exchangeRate.setTimestamp(LocalDateTime.parse(o[4], DateTimeFormatter.ofPattern(timestampFormat)));
                    return exchangeRate;
                })
                .collect(Collectors.toList());
    }

    private static void trim(String[] array) {
        // Trim each string in the array
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
        }
    }
}
