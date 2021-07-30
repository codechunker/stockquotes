package com.tcs.stockquotes.dto;

public class StockResponse {

    private String content;

    public StockResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
