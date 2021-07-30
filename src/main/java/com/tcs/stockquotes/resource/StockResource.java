package com.tcs.stockquotes.resource;

import com.tcs.stockquotes.dto.StockDTO;
import com.tcs.stockquotes.dto.StockResponse;
import com.tcs.stockquotes.service.StockQuoteService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

@Controller
public class StockResource {

    final StockQuoteService stockQuoteService;

    public StockResource(StockQuoteService stockQuoteService) {
        this.stockQuoteService = stockQuoteService;
    }

    @MessageMapping("/add")
    @SendTo("/topic/user")
    public StockResponse addStock(StockDTO stockDTO){

        if (stockDTO != null && stockDTO.getName() != null && !stockDTO.getName().isEmpty()) {
            Set<String> stocks = stockQuoteService.addStockToWatch(stockDTO.getName());
            return new StockResponse(String.format("Stock to watch is: %s", stocks));
        }
        return null;
    }

    @MessageMapping("/remove")
    @SendTo("/topic/user")
    public StockResponse removeStock(StockDTO stockDTO){
        stockQuoteService.removeStockToWatch(stockDTO.getName());
        return new StockResponse(!stockDTO.getName().isEmpty() ? String.format("Stock removed is: %s", stockDTO.getName()) : "");
    }

    @MessageMapping("/average")
    @SendTo("/topic/user")
    public StockResponse getPriceAvg() throws IOException {
        BigDecimal avgPrice = stockQuoteService.getAveragePrice();
        return new StockResponse(String.format("Stock Price Average: %s", avgPrice));
    }


}
