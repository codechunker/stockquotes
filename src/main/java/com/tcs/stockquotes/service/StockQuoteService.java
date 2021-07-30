package com.tcs.stockquotes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class StockQuoteService {

    private static final Logger log = LoggerFactory.getLogger(StockQuoteService.class);
    Set<String> stocksToWatch =  new LinkedHashSet<>();

    List<BigDecimal> stockPrices = new ArrayList<>();

    public Set<String> addStockToWatch(String stockName) {
        stockName = stockName.toUpperCase();
        stocksToWatch.add(stockName);
        return stocksToWatch;
    }

    public StockQuote getStock(String stockName) {
        StockQuote stockQuote = null;
        try {
            Stock stock = YahooFinance.get(stockName);
            stockQuote = stock.getQuote();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockQuote;
    }

    public Map<String, StockQuote> getStockListByNames() throws IOException {
        if (!stocksToWatch.isEmpty()) {
            Object[] objects = stocksToWatch.toArray();
            String[] symbols = convertObjectArrayToStringArray(objects);
            Map<String, Stock> stocks = YahooFinance.get(symbols); // single request

            return convertMapValueToList(stocks);
        }
        return null;
    }


    public void removeStockToWatch(String name) {
        if (name == null || name.isEmpty()) return;
        name = name.toUpperCase();
        if (stocksToWatch != null && !stocksToWatch.isEmpty() && stocksToWatch.contains(name)) {
            stocksToWatch.remove(name);
            log.info("Stock: "+name + " removed");
        }
    }

    public BigDecimal getAveragePrice() throws IOException {
        Map<String, StockQuote> stocksByNames = getStockListByNames();
        BigDecimal totalAvg = BigDecimal.ZERO;
        if (stocksByNames != null && !stocksByNames.isEmpty()) {
            for (Map.Entry<String, StockQuote> stock : stocksByNames.entrySet()) {
                totalAvg = totalAvg.add(stock.getValue().getPrice());
            }

            return totalAvg.compareTo(BigDecimal.ZERO) > 0 ? totalAvg.divide(new BigDecimal(stocksByNames.size())) : BigDecimal.ZERO;

        }

        return BigDecimal.ZERO;
    }

    private String[] convertObjectArrayToStringArray(Object[] objects) {
        String[] arrayReturn = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            arrayReturn[i] = (String)objects[i];
        }

        return arrayReturn;
    }

    private Map<String, StockQuote> convertMapValueToList(Map<String, Stock> map) {
        Map<String, StockQuote> stockQuoteMap = new HashMap<>();
        for(Map.Entry<String, Stock> stock : map.entrySet()) {
            stockQuoteMap.put(stock.getKey(), stock.getValue().getQuote());
        }

        return stockQuoteMap;
    }
}
