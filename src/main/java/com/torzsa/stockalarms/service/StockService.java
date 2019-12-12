package com.torzsa.stockalarms.service;

import com.torzsa.stockalarms.model.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {

    //    List<Stock> getStocksByUsername(String username);

    List<Stock> findAll();

    List<Stock> getStocksByUser(String username);

    Optional<Stock> getStockById(long id);

    List<Stock> getStockBySymbol(String symbol);

    boolean existsStockBySymbol(String symbol);

    void deleteStock(long id);

    void saveStock(Stock Stock);
}
