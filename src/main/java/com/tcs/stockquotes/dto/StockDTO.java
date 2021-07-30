package com.tcs.stockquotes.dto;

import java.io.Serializable;

public class StockDTO implements Serializable {

    private String name;

    public StockDTO(String name) {
        this.name = name;
    }

    public StockDTO() {
    }

    public String getName() {
        return name;
    }
}
