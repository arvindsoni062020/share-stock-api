package com.hcl.sharestockapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.sharestockapi.model.PriceInput;
import com.hcl.sharestockapi.service.StockPriceService;

@RestController
@RequestMapping("stock")
public class StockPriceController {
	@Autowired
	StockPriceService stockPriceService;
	
	@PostMapping("/buy")
	ResponseEntity<Double> buyShare(@RequestBody PriceInput price){
		 //String  priceStr ="9.20 8.03 10.02 8.08 8.14 8.10 8.31 8.28 8.35 8.34 8.39 8.45 8.38 8.38 8.32 8.36 8.28 8.28 8.38 8.48 8.49 8.54 8.73 8.72 8.76 8.74 8.87 8.82 8.81 8.82 8.85 8.85 8.86 8.63 8.70 8.68 8.72 8.77 8.69 8.65 8.70 8.98 8.98 8.87 8.71 9.17 9.34 9.28 8.98 9.02 9.16 9.15 9.07 9.14 9.13 9.10 9.16 9.06 9.10 9.15 9.11 8.72 8.86 8.83 8.70 8.69 8.73 8.73 8.67 8.70 8.69 8.81 8.82 8.83 8.91 8.80 8.97 8.86 8.81 8.87 8.82 8.78 8.82 8.77 8.54 8.32 8.33 8.32 8.51 8.53 8.52 8.41 8.55 8.31 8.38 8.34 8.34 8.19 8.17 8.16";
		  Double priceDoub =stockPriceService.buyShare(price.getPrice());
		 return new ResponseEntity<Double>(priceDoub, HttpStatus.CREATED);
	}
	
	@PostMapping("/sale/{id}")
	ResponseEntity<Double> saleShare(@RequestBody PriceInput price, @PathVariable("id") Long id){
		
		 Double priceDbl =stockPriceService.saleShare(price.getPrice(), id);
		 return new ResponseEntity<Double>(priceDbl, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/pricelist")
	ResponseEntity<List<Double>> getSharePrices(){
		List<Double> list = new ArrayList<>();
		list =stockPriceService.getSharePriceList();
		return new ResponseEntity<List<Double>>(list, HttpStatus.CREATED);
		
	}

}
