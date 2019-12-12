package com.torzsa.stockalarms.model;


import java.util.Date;

public class StockDto {
    private long id;
    private String symbol;
    private String companyName;
    private float price;
    private Date lastSystemUpdate;
    private boolean hasAlarm;
    private float targetPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(float targetPrice) {
        this.targetPrice = targetPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getLastSystemUpdate() {
        return lastSystemUpdate;
    }

    public void setLastSystemUpdate(Date lastSystemUpdate) {
        this.lastSystemUpdate = lastSystemUpdate;
    }

    public boolean isHasAlarm() {
        return hasAlarm;
    }

    public void setHasAlarm(boolean hasAlarm) {
        this.hasAlarm = hasAlarm;
    }
}
