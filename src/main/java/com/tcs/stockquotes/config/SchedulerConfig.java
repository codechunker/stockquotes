package com.tcs.stockquotes.config;

import com.tcs.stockquotes.dto.StockResponse;
import com.tcs.stockquotes.service.StockQuoteService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableScheduling
@Configuration
public class SchedulerConfig {

    final SimpMessagingTemplate template;
    final StockQuoteService stockQuoteService;

    List<String> stocksToWatch = new ArrayList<>();

    public SchedulerConfig(SimpMessagingTemplate template, StockQuoteService stockQuoteService) {
        this.template = template;
        this.stockQuoteService = stockQuoteService;
    }

    @Scheduled(fixedDelay = 3000)
    public void sendAdhocMessage() throws IOException {
        Map<String, StockQuote> stockQuotes = stockQuoteService.getStockListByNames();
        if (stockQuotes != null && !stockQuotes.isEmpty()) {
            template.convertAndSend("/topic/user", new StockResponse(stockQuotes.toString()));
        }
    }
}
