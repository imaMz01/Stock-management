package com.service.product.Repositories;

import com.service.product.Entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,String> {
    Optional<Stock> findByIdManager(String id);
}
