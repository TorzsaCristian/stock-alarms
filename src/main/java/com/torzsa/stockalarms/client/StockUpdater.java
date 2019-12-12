package com.torzsa.stockalarms.client;

import com.torzsa.stockalarms.controller.UserController;
import com.torzsa.stockalarms.model.GlobalQuote;
import com.torzsa.stockalarms.model.Stock;
import com.torzsa.stockalarms.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockUpdater {


    private static final Logger LOGGER = LoggerFactory.getLogger(StockUpdater.class);

    private static final int MAX_STOCKS_PER_MINUTE = 5;

    @Autowired
    private AlphavantageClient alphavantageClient;

    @Autowired
    private StockService stockService;

    @Scheduled(fixedRateString = "${stock.poll-rate}")
    public void updateStocks() throws InterruptedException {
        List<Stock> stocks = stockService.findAll();

        LOGGER.debug("Starting updating stocks");


        int apiRequests = 0;
        for (Stock stock : stocks) {
            // Wait for safety? may trigger DDOS protection?
            Thread.sleep(100);
            LOGGER.debug("Loading data for stock " + stock.getSymbol());


            try {
                GlobalQuote quote = alphavantageClient.getQuoteForSymbol(stock.getSymbol());
                LOGGER.debug("Loaded quote: " + quote);

                apiRequests++;

                Stock updatedStock = new Stock();
                updatedStock.setSymbol(quote.getSymbol());
                updatedStock.setPrice(quote.getPrice());
                stockService.saveStock(updatedStock);
            } catch (Exception e) {
                LOGGER.error("Quote request failed");
            }

            if (apiRequests == MAX_STOCKS_PER_MINUTE) {
                Thread.sleep(70000);
                apiRequests = 0;
            }
        }

    }
}
