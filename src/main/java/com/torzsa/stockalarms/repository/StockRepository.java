package com.torzsa.stockalarms.repository;

import com.torzsa.stockalarms.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAll();

    List<Stock> findBySymbol(String symbol);

    boolean existsStockBySymbol(String symbol);

    @Query(value = "SELECT u.stocks FROM User u WHERE u.id = :id")
    List<Stock> findByUserId(@Param("id") long id);

}
