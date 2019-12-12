package com.torzsa.stockalarms.controller;

import com.torzsa.stockalarms.client.AlphavantageClient;
import com.torzsa.stockalarms.model.*;
import com.torzsa.stockalarms.service.AlarmService;
import com.torzsa.stockalarms.service.StockService;
import com.torzsa.stockalarms.service.UserService;
import com.torzsa.stockalarms.utils.Utils;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private UserService userService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private AlphavantageClient alphavantageClient;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/list-stocks", method = RequestMethod.GET)
    public String listStocks(ModelMap model) {
        List<Stock> stocks = stockService.getStocksByUser(Utils.getLoggedInUsername());
        List<Alarm> alarms = alarmService.getAlarmsByUser(Utils.getLoggedInUsername());

        List<Stock> stock_with_alarms = alarms.stream().map(Alarm::getStock).collect(Collectors.toList());

        List<StockDto> stockDtos = stocks.stream().map(stock -> {
            StockDto stockDto = new StockDto();
            stockDto.setId(stock.getId());
            stockDto.setCompanyName(stock.getCompanyName());
            stockDto.setSymbol(stock.getSymbol());
            stockDto.setPrice(stock.getPrice());
            stockDto.setLastSystemUpdate(stock.getLatestUpdateBySystem());

            stockDto.setHasAlarm(stock_with_alarms.contains(stock));
            return stockDto;
        }).collect(Collectors.toList());


        model.put("stocks", stockDtos);
        model.addAttribute("alarmForm", new AlarmForm());
        return "stock/list-stocks";
    }

    @RequestMapping(value = "/add-stock", method = RequestMethod.GET)
    public String showAddStockPage(ModelMap model) {
        model.addAttribute("stock", new Stock());
        return "stock/stock";
    }

    @RequestMapping(value = "/delete-stock", method = RequestMethod.GET)
    public String deleteStock(@RequestParam long id) throws NotFoundException {
//        stockService.deleteStock(id);

        Stock stock = stockService.getStockById(id).orElseThrow(() -> new NotFoundException("Not found Stock with ID " + id));


        User loggedUser = userService.findByEmail(Utils.getLoggedInUsername());
        loggedUser.getStocks().remove(stock);
        userService.save(loggedUser);

        return "redirect:/list-stocks";
    }

    @RequestMapping(value = "/update-stock", method = RequestMethod.GET)
    public String editStockView(@RequestParam long id, ModelMap model) throws NotFoundException {
        Stock stock = stockService.getStockById(id)
                .orElseThrow(() -> new NotFoundException("Not found Stock with ID " + id));

        model.addAttribute("stock", stock);
        return "stock/stock";
    }

    @RequestMapping(value = "/update-stock", method = RequestMethod.POST)
    public String updateStock(ModelMap model, @Valid @ModelAttribute("stock") Stock stock, BindingResult result) {
        if (result.hasErrors()) {
            return "stock/stock";
        }

//        stock.setUser(getLoggedInUser());
        stockService.saveStock(stock);
        return "redirect:/list-stocks";
    }

    @RequestMapping(value = "/add-stock", method = RequestMethod.POST)
    public String addStock(ModelMap model, @Valid Stock stock, BindingResult result) {


        String stockSymbol = stock.getSymbol();

        if (stockService.existsStockBySymbol(stockSymbol)) {
            stock = stockService.getStockBySymbol(stockSymbol).get(0);
        }


        // TODO: 11/12/2019 Make request reactive
        // Update Stock
        GlobalQuote quote = null;
        try {
            quote = alphavantageClient.getQuoteForSymbol(stock.getSymbol());
            LOGGER.debug("Quote: " + quote);

            stock.setSymbol(quote.getSymbol());
            stock.setPrice(quote.getPrice());

            // Save Stock
            stockService.saveStock(stock);

            // Save stock to user stocks list
            User loggedUser = userService.findByEmail(Utils.getLoggedInUsername());
            loggedUser.getStocks().add(stock);
            userService.save(loggedUser);

        } catch (HttpClientErrorException ex) {
            result.rejectValue("symbol", "error.symbol", "Unknown symbol");
        }

        if (result.hasErrors()) {
            return "stock/stock";
        }

        return "redirect:/list-stocks";
    }


}