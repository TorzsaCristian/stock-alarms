package com.torzsa.stockalarms.controller;

import com.torzsa.stockalarms.model.AlarmForm;
import com.torzsa.stockalarms.model.StockAlarmKey;
import com.torzsa.stockalarms.service.AlarmService;
import com.torzsa.stockalarms.service.AlarmServiceImpl;
import com.torzsa.stockalarms.service.EmailService;
import com.torzsa.stockalarms.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
//@RequestMapping("/alarms")
public class AlarmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private AlarmService alarmService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/add-alarm", method = RequestMethod.POST)
    public String addStock(ModelMap model, @Valid AlarmForm alarmForm, BindingResult result) {
        LOGGER.debug("Alarm condition from request: " + alarmForm.getCondition());
        alarmService.createAlarmFor(alarmForm.getStockSymbol(), Utils.getLoggedInUsername(), alarmForm.getCondition());

        if (result.hasErrors()) {
            return "stock/stock";
        }

        return "redirect:/list-stocks";

    }

    @RequestMapping(value = "/list-alarms", method = RequestMethod.GET)
    public String listStocks(ModelMap model) {
        model.put("alarms", alarmService.getAlarmsByUser(Utils.getLoggedInUsername()));
        return "alarm/list-alarms";
    }


    @RequestMapping(value = "/delete-alarm", method = RequestMethod.GET)
    public String deleteStock(@RequestParam long id) {
        alarmService.deleteUserAlarmForStockId(id);
        return "redirect:/list-stocks";
    }


}
