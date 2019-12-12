package com.torzsa.stockalarms.service;

import com.torzsa.stockalarms.model.Alarm;
import com.torzsa.stockalarms.model.Stock;
import com.torzsa.stockalarms.model.StockAlarmKey;
import com.torzsa.stockalarms.model.User;
import com.torzsa.stockalarms.repository.AlarmRepository;
import com.torzsa.stockalarms.repository.StockRepository;
import com.torzsa.stockalarms.repository.UserRepository;
import com.torzsa.stockalarms.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AlarmServiceImpl implements AlarmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmServiceImpl.class);

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;


    @Override
    public List<Alarm> getAlarmsByUser(String username) {
        return alarmRepository.findByUserId(userRepository.findByEmail(username).getId());
    }

    @Override
    public Alarm findAlarmById(StockAlarmKey id) {
        return alarmRepository.findAlarmById(id);
    }

    @Override
    public Alarm getUserAlarmForStock(String username, Stock stock) {
        StockAlarmKey sak = new StockAlarmKey();
        sak.setUserId(userRepository.findByEmail(username).getId());
        sak.setStockId(stock.getId());

        return alarmRepository.findAlarmById(sak);
    }

    @Override
    public Alarm createAlarmFor(String stockSymbol, String username, float percent) {
        Stock stock = stockRepository.findBySymbol(stockSymbol).get(0);
        User user = userRepository.findByEmail(username);

        StockAlarmKey sak = new StockAlarmKey();
        sak.setStockId(stock.getId());
        sak.setUserId(user.getId());

        LOGGER.debug("SAK KEY ID: " + sak.toString());

        Alarm alarm = new Alarm();
        alarm.setId(sak);
        alarm.setUser(user);
        alarm.setStock(stock);
        alarm.setStartPrice(stock.getPrice());
        alarm.setActive(true);
        alarm.setEndPrice(getTargetPrice(stock.getPrice(), percent));

        saveAlarm(alarm);
        LOGGER.debug("Alarm saved: " + alarm.toString());

        return alarm;
    }

    @Override
    public List<Alarm> findAlarmsByStock(Stock stock) {
        return findAlarmsByStockId(stock.getId());
    }

    @Override
    public List<Alarm> findAlarmsByStockId(long id) {
        return alarmRepository.findByStockId(id);
    }

    @Override
    public void deleteUserAlarmForStockId(long id) {
        StockAlarmKey sak = new StockAlarmKey();
        sak.setStockId(id);
        sak.setUserId(userRepository.findByEmail(Utils.getLoggedInUsername()).getId());
        alarmRepository.deleteById(sak);
    }

    @Override
    public void updateAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    @Override
    public void deleteAlarm(StockAlarmKey id) {
        Optional<Alarm> alarm = alarmRepository.findById(id);
        alarm.ifPresent(alarm1 -> alarmRepository.delete(alarm1));
    }

    @Override
    public void saveAlarm(Alarm newAlarm) {
        Alarm alarm = alarmRepository.findAlarmById(newAlarm.getId());
        if (alarm == null) {
            alarm = new Alarm();
        }
        alarm.setEndPrice(newAlarm.getEndPrice());
        alarm.setStartPrice(newAlarm.getStartPrice());
        alarm.setStock(newAlarm.getStock());
        alarm.setUser(newAlarm.getUser());
        alarm.setActive(newAlarm.isActive());
        alarm.setCreatedDate(Utils.convertTimestampToDate(System.currentTimeMillis()));
        alarmRepository.save(alarm);
    }

    public float getTargetPrice(float startPrice, float percentage) {
        float variation = startPrice * percentage / 100;
        return startPrice + variation;
    }

}
