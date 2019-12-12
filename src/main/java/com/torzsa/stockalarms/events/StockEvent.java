package com.torzsa.stockalarms.events;

import com.torzsa.stockalarms.model.Stock;
import org.springframework.context.ApplicationEvent;

public class StockEvent extends ApplicationEvent {

    private Stock stock;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public StockEvent(Object source, Stock stock) {
        super(source);
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock alarm) {
        this.stock = stock;
    }
}
