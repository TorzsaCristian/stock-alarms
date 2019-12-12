package com.torzsa.stockalarms.events;

import com.torzsa.stockalarms.client.AlphavantageClientImpl;
import com.torzsa.stockalarms.model.Alarm;
import com.torzsa.stockalarms.model.Stock;
import com.torzsa.stockalarms.service.AlarmService;
import com.torzsa.stockalarms.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockUpdateListener implements ApplicationListener<StockEvent> {


    private static final Logger LOGGER = LoggerFactory.getLogger(StockUpdateListener.class);


    @Autowired
    private AlarmService alarmService;


    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(StockEvent event) {
        LOGGER.debug("Stock event triggered by " + event.getStock());

        Stock newStock = event.getStock();

        List<Alarm> alarms = alarmService.findAlarmsByStock(newStock);

        List<Alarm> alarms_to_send = new ArrayList<>();

        for (Alarm alarm : alarms) {
            if (alarm.getStartPrice() <= alarm.getEndPrice()) {
                // User choose +
                if (newStock.getPrice() >= alarm.getEndPrice()) {
                    alarm.setActive(false);
                    alarms_to_send.add(alarm);
                }
            } else {
                // User choose -
                if (newStock.getPrice() <= alarm.getEndPrice()) {
                    alarm.setActive(false);
                    alarms_to_send.add(alarm);
                }
            }
            alarmService.saveAlarm(alarm);
        }

        emailService.send(alarms_to_send);
    }
}
