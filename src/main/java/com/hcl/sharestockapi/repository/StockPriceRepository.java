package com.hcl.sharestockapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.sharestockapi.model.StockPrice;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
	
	StockPrice findByUserIdAndPrice(Integer userId, Double price);

}

