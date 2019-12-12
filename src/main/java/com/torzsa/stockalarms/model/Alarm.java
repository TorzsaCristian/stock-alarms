package com.torzsa.stockalarms.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "alarms")
public class Alarm {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @EmbeddedId
    private StockAlarmKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("stock_id")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private boolean isActive;

    private float startPrice;

    private float endPrice;

    @CreatedDate
    private Date createdDate;

    public StockAlarmKey getId() {
        return id;
    }

    public void setId(StockAlarmKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(float startPrice) {
        this.startPrice = startPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public float getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(float endPrice) {
        this.endPrice = endPrice;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "user=" + user.getEmail() +
                ", stock=" + stock.getSymbol() +
                ", startPrice=" + startPrice +
                ", endPrice=" + endPrice +
                '}';
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
