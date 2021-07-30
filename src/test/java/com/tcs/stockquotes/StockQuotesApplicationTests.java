package com.tcs.stockquotes;

import com.tcs.stockquotes.service.StockQuoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockQuotesApplicationTests {

    @Autowired
    StockQuoteService stockQuoteService;

    @Test
    public void givenStockName_WhenAddStockToWatch_ThenAssertNotNull() {
        assertThat(stockQuoteService.addStockToWatch("INTC")).isNotNull();
    }

    @Test
    public void givenStockName_WhenAddStockToWatch_ThenReturnSet() {
        Set<String> intc = stockQuoteService.addStockToWatch("INTC");
        assertThat(intc).hasSize(1);
    }

    @Test
    public void given_WhenGetStockListByName_ThenReturnStockListByName() throws IOException {
        stockQuoteService.addStockToWatch("INTC");
        assertThat(stockQuoteService.getStockListByNames()).hasSize(1);
    }

    @Test
    public void givenStockName_WhenRemoveStockToWatch_ThenReturnEmptyList() throws IOException {
        stockQuoteService.addStockToWatch("INTC");
        stockQuoteService.removeStockToWatch("INTC");
        assertThat(stockQuoteService.getStockListByNames()).isNull();
    }

    @Test
    public void given_WhenGetAveragePrice_ThenReturnAveragePrice() throws IOException {
        stockQuoteService.addStockToWatch("INTC");
        stockQuoteService.addStockToWatch("BABA");
        assertThat(stockQuoteService.getAveragePrice()).isGreaterThanOrEqualTo(new BigDecimal("125.62"));
    }

}
