package com.torzsa.stockalarms.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String symbol;

    private String companyName;

    private float open; // open price

    private float close; // close price

    private float price;

    // StockAlarms latest price update
    private Date latestUpdateBySystem;

    // Stock latest update from API
    private Date latestUpdate;

    @ManyToMany(mappedBy = "stocks")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "stock")
    private Set<Alarm> alarms;


    public Stock() {
        super();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Date getLatestUpdateBySystem() {
        return latestUpdateBySystem;
    }

    public void setLatestUpdateBySystem(Date latestUpdateBySystem) {
        this.latestUpdateBySystem = latestUpdateBySystem;
    }

    public Date getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(Date latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public Set<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(Set<Alarm> alarms) {
        this.alarms = alarms;
    }
}
