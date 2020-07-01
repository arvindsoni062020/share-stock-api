package com.hcl.sharestockapi.service;

import java.util.List;

import com.hcl.sharestockapi.model.StockPrice;

public interface StockPriceService {
	
	public Double buyShare(String price);
	public Double saleShare(String price, Long id);
	List<Double> getSharePriceList();

}
