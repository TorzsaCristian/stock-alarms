package com.torzsa.stockalarms.service;

import com.torzsa.stockalarms.model.Alarm;
import com.torzsa.stockalarms.model.Stock;
import com.torzsa.stockalarms.model.StockAlarmKey;

import java.util.List;
import java.util.Optional;

public interface AlarmService {

    List<Alarm> getAlarmsByUser(String username);

    Alarm findAlarmById(StockAlarmKey id);

    Alarm getUserAlarmForStock(String username, Stock stock);

    Alarm createAlarmFor(String stockSymbol, String username, float percent);

    List<Alarm> findAlarmsByStock(Stock stock);

    List<Alarm> findAlarmsByStockId(long id);

    void deleteUserAlarmForStockId(long id);

    void updateAlarm(Alarm alarm);

    void deleteAlarm(StockAlarmKey id);

    void saveAlarm(Alarm alarm);
}
