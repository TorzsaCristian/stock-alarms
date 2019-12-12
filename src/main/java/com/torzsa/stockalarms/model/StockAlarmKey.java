package com.torzsa.stockalarms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StockAlarmKey implements Serializable {

    /*  Composite Key for requirement in UC2:
     *  - for each Stock the can define ONE alarm
     */

    @Column(name = "user_id")
    private
    Long userId;

    @Column(name = "stock_id")
    private
    Long stockId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (getClass() != obj.getClass())
            return false;

        StockAlarmKey sak = (StockAlarmKey) obj;
        return (this.getStockId().equals(sak.getStockId()) && this.getUserId().equals(sak.getUserId()));
    }


    @Override
    public int hashCode() {
        int hash = 17;
        int hashMultiplikator = 79;
        int hashSum = stockId.hashCode() + userId.hashCode();
        hash = hashMultiplikator * hash * hashSum;
        return hash;
    }

    @Override
    public String toString() {
        return "StockAlarmKey{" +
                "userId=" + userId +
                ", stockId=" + stockId +
                '}';
    }
}
