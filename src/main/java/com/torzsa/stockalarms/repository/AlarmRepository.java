package com.torzsa.stockalarms.repository;

import com.torzsa.stockalarms.model.Alarm;
import com.torzsa.stockalarms.model.Stock;
import com.torzsa.stockalarms.model.StockAlarmKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, StockAlarmKey> {
    List<Alarm> findByUserId(long id);

    Alarm findAlarmById(StockAlarmKey id);

    @Query(value = "SELECT s.alarms FROM Stock s WHERE s.id = :id")
    List<Alarm> findByStockId(@Param("id") long id);


}
