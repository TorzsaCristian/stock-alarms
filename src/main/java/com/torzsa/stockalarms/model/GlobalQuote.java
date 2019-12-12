package com.torzsa.stockalarms.model;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "Global Quote")
public class GlobalQuote {

    @JsonAlias({"01. symbol"})
    private String symbol;

    @JsonAlias({"02. open"})
    private float open;

    @JsonAlias({"03. high"})
    private float high;

    @JsonAlias({"04. low"})
    private float low;

    @JsonAlias({"05. price"})
    private float price;

    @JsonAlias({"06. volume"})
    private long volume;

    @JsonAlias({"07. latest trading day"})
    private String latestTradingDay;

    @JsonAlias({"08. previous close"})
    private float previousClose;

    @JsonAlias({"09. change"})
    private float change;

    @JsonAlias({"10. change percent"})
    private String changePercent;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getLatestTradingDay() {
        return latestTradingDay;
    }

    public void setLatestTradingDay(String latestTradingDay) {
        this.latestTradingDay = latestTradingDay;
    }

    public float getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(float previousClose) {
        this.previousClose = previousClose;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    @Override
    public String toString() {
        return "GlobalQuote{" +
                "symbol='" + symbol + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
