package com.torzsa.stockalarms.service;

import com.torzsa.stockalarms.events.StockEvent;
import com.torzsa.stockalarms.model.Stock;
import com.torzsa.stockalarms.repository.StockRepository;
import com.torzsa.stockalarms.repository.UserRepository;
import com.torzsa.stockalarms.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public List<Stock> getStocksByUser(String username) {
        return stockRepository.findByUserId(userRepository.findByEmail(username).getId());
    }

    @Override
    public Optional<Stock> getStockById(long id) {
        return stockRepository.findById(id);
    }

    @Override
    public List<Stock> getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

    @Override
    public boolean existsStockBySymbol(String symbol) {
        return stockRepository.existsStockBySymbol(symbol);
    }

//    @Override
//    public List<Stock> getStocksByUsername(String username) {
//        User user = userRepository.findByEmail(username);
//        return stockRepository.findByUser(user);
//    }


    @Override
    public void deleteStock(long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        stock.ifPresent(stock1 -> stockRepository.delete(stock1));
    }

    @Override
    public void saveStock(Stock newStock) {
        Stock stock = stockRepository.findBySymbol(newStock.getSymbol()).get(0);
        if (stock == null) {
            stock = new Stock();
        }

        stock.setSymbol(newStock.getSymbol());
        stock.setPrice(newStock.getPrice());
        stock.setLatestUpdateBySystem(Utils.convertTimestampToDate(System.currentTimeMillis()));

        applicationEventPublisher.publishEvent(new StockEvent(this, stock));

        stockRepository.save(stock);
    }
}
