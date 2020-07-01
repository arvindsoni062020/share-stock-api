/**
 * 
 */
package com.hcl.sharestockapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hcl.sharestockapi.model.Price;
import com.hcl.sharestockapi.model.StockPrice;
import com.hcl.sharestockapi.repository.StockPriceRepository;

/**
 * @author arvind.so
 *
 */
@Service
public class StockPriceServiceImpl implements StockPriceService {

	@Resource
	StockPriceRepository repository;

	@Override
	public Double buyShare(String price) {
		StockPrice stockPrice = null;
		StockPrice minStockPrice =null;
		List<Price> priceInput = getListFromArray(price);
		// by share with min price
		OptionalDouble minPrice = priceInput.stream().mapToDouble(Price::getPrice).min();
		if (minPrice.isPresent()) {
			stockPrice = new StockPrice();
			stockPrice.setName("HCL");
			stockPrice.setUserId(1);
			stockPrice.setPrice(minPrice.getAsDouble());
		}
		minStockPrice =repository.findByUserIdAndPrice(1, 8.03);
		if(minStockPrice==null) {
		return repository.save(stockPrice).getPrice();
		}else {
			return stockPrice.getPrice();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hcl.sharestockapi.service.StockService#saleShare()
	 */
	@Override
	public Double saleShare(String priceStr, Long id) {
		OptionalDouble optionalMaxPrice = null;
		StockPrice maxStockPrice =null;
		StockPrice stockPrice =null;
		// will check if purchased record available
		Optional<StockPrice> optional = repository.findById(id);
		if (optional.isPresent()) {
			List<Price> priceInput = getListFromArray(priceStr);
			// by share with max price
			optionalMaxPrice = priceInput.stream().mapToDouble(Price::getPrice).max();
			if(optionalMaxPrice.isPresent()) {
			stockPrice = new StockPrice();
			stockPrice.setName("HCL");
			stockPrice.setPrice(optionalMaxPrice.getAsDouble());
			stockPrice.setUserId(1);
			}
		}
		maxStockPrice =repository.findByUserIdAndPrice(1, 10.02);
		if(maxStockPrice==null) {
		 return repository.save(stockPrice).getPrice();
		}else {
			return stockPrice.getPrice();
		}
	}

	@Override
	public List<Double> getSharePriceList() {
		List<StockPrice> tradingList = new ArrayList<>();
		repository.findAll().forEach(sprice -> tradingList.add(sprice));
		return tradingList.stream().map(p->p.getPrice()).collect(Collectors.toList());
	}

	List<Price> getListFromArray(String price) {
		StringTokenizer st = new StringTokenizer(price);
		List<Price> dprice = new ArrayList<>();
		while (st.hasMoreTokens()) {
			dprice.add(new Price(Double.parseDouble(st.nextToken())));
		}
		return dprice;
	}

}
