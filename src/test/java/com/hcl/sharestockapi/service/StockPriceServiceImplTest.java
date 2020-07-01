package com.hcl.sharestockapi.service;


import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

//import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hcl.sharestockapi.model.Price;
import com.hcl.sharestockapi.model.StockPrice;
import com.hcl.sharestockapi.repository.StockPriceRepository;

public class StockPriceServiceImplTest {

	@InjectMocks
	private StockPriceServiceImpl stockPriceServiceImpl;
	@Mock
	private StockPriceRepository stockPriceRepository;
	
	 @BeforeAll
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	  }
	 @Test
	 public void getStockPriceListTest()
	    {
		     List<StockPrice> priceList = new ArrayList<>();
		     StockPrice stockPrice1 = new StockPrice();
		     stockPrice1.setName("HCLTest");
		     stockPrice1.setPrice(Double.valueOf(8.03));
		     stockPrice1.setUserId(1);
		     StockPrice stockPrice2 = new StockPrice();
		     stockPrice2.setName("HCLTest");
		     stockPrice2.setPrice(Double.valueOf(10.02));
		     stockPrice2.setUserId(1);
		     priceList.add(stockPrice1);
		     priceList.add(stockPrice2);
		     
	        when(stockPriceRepository.findAll()).thenReturn(priceList);
	         
	        List<Double> list = stockPriceServiceImpl.getSharePriceList();
	         
	        assertEquals(2, list.size());
	        assertEquals(Double.valueOf(8.03), list.get(0));
	        assertEquals(10.02, list.get(1));
	    }
	 
	 // when same share not buy 
	 @Test
	 public void buyShareWhenShareNotBuyTest()
	    {
		 
		     String priceTest= "9.20 8.03 10.02 8.08";
		     
		     List<Price> priceInput = new ArrayList<>();
		     priceInput.add(new Price(8.03));
		     priceInput.add(new Price(8.08));
		     priceInput.add(new Price(9.20));
		     priceInput.add(new Price(10.02));
		     
		     when(stockPriceServiceImpl.getListFromArray(priceTest)).thenReturn(priceInput);
		     assertEquals(4, priceInput.size());
		     StockPrice stockPrice =null;
		     StockPrice minStockPrice =null;
		     OptionalDouble minPrice =OptionalDouble.of(8.03);
		     when(priceInput.stream().mapToDouble(Price::getPrice).min()).thenReturn(minPrice);
		     if(minPrice.isPresent()) {
		    	 stockPrice = new StockPrice();
		    	 stockPrice.setName("HCLTest");
				 stockPrice.setUserId(1);
				 stockPrice.setPrice(minPrice.getAsDouble());
		     }
		     
		     when(stockPriceRepository.findByUserIdAndPrice(1, 8.03)).thenReturn(minStockPrice);
		     Mockito.verify(stockPriceRepository.save(stockPrice).getPrice());
	    }
	 
	 
	    // when same share try to buy again 
		 @Test
		 public void buyShareWhenShareAlreadyBuyTest()
		    {
			 
			     String priceTest= "9.20 8.03 10.02 8.08";
			     
			     List<Price> priceInput = new ArrayList<>();
			     priceInput.add(new Price(8.03));
			     priceInput.add(new Price(8.08));
			     priceInput.add(new Price(9.20));
			     priceInput.add(new Price(10.02));
			     
			     when(stockPriceServiceImpl.getListFromArray(priceTest)).thenReturn(priceInput);
			     assertEquals(4, priceInput.size());
			     StockPrice stockPrice =null;
			     StockPrice minStockPrice =null;
			     OptionalDouble minPrice =OptionalDouble.of(8.03);
			     when(priceInput.stream().mapToDouble(Price::getPrice).min()).thenReturn(minPrice);
			     if(minPrice.isPresent()) {
			    	 stockPrice = new StockPrice();
			    	 stockPrice.setName("HCLTest");
					 stockPrice.setUserId(1);
					 stockPrice.setPrice(minPrice.getAsDouble());
			     }
			     
			     minStockPrice = new StockPrice();
			     minStockPrice.setName("HCLTest");
			     minStockPrice.setUserId(1);
			     minStockPrice.setPrice(minPrice.getAsDouble());
			     
			     when(stockPriceRepository.findByUserIdAndPrice(1, 8.03)).thenReturn(minStockPrice);
			     assertEquals(8.03, stockPrice.getPrice());
		    }
		 
		// Sale share after Buy 
		 @Test
		 public void saleShareAfterBuyTest()
		    {
			 
			     String priceTest= "9.20 8.03 10.02 8.08";
			     
			     List<Price> priceInput = new ArrayList<>();
			     priceInput.add(new Price(8.03));
			     priceInput.add(new Price(8.08));
			     priceInput.add(new Price(9.20));
			     priceInput.add(new Price(10.02));
			     
			     when(stockPriceServiceImpl.getListFromArray(priceTest)).thenReturn(priceInput);
			     assertEquals(4, priceInput.size());
			     StockPrice stockPrice =null;
			     StockPrice minStockPrice =null;
			     OptionalDouble maxPrice =OptionalDouble.of(10.02);
			     when(priceInput.stream().mapToDouble(Price::getPrice).max()).thenReturn(maxPrice);
			     if(maxPrice.isPresent()) {
			    	 stockPrice = new StockPrice();
			    	 stockPrice.setName("HCLTest");
					 stockPrice.setUserId(1);
					 stockPrice.setPrice(maxPrice.getAsDouble());
			     }
			     
			     when(stockPriceRepository.findByUserIdAndPrice(1, 10.02)).thenReturn(minStockPrice);
			     Mockito.verify(stockPriceRepository.save(stockPrice).getPrice());
		    }
		 
		 
		 // when same share sale again 
		 @Test
		 public void saleShareWhenShareAlreadysoledTest()
		    {
			 
			     String priceTest= "9.20 8.03 10.02 8.08";
			     
			     List<Price> priceInput = new ArrayList<>();
			     priceInput.add(new Price(8.03));
			     priceInput.add(new Price(8.08));
			     priceInput.add(new Price(9.20));
			     priceInput.add(new Price(10.02));
			     
			     when(stockPriceServiceImpl.getListFromArray(priceTest)).thenReturn(priceInput);
			     assertEquals(4, priceInput.size());
			     StockPrice stockPrice =null;
			     StockPrice minStockPrice =null;
			     OptionalDouble maxPrice =OptionalDouble.of(10.02);
			     when(priceInput.stream().mapToDouble(Price::getPrice).min()).thenReturn(maxPrice);
			     if(maxPrice.isPresent()) {
			    	 stockPrice = new StockPrice();
			    	 stockPrice.setName("HCLTest");
					 stockPrice.setUserId(1);
					 stockPrice.setPrice(maxPrice.getAsDouble());
			     }
			     
			     minStockPrice = new StockPrice();
			     minStockPrice.setName("HCLTest");
			     minStockPrice.setUserId(1);
			     minStockPrice.setPrice(maxPrice.getAsDouble());
			     
			     when(stockPriceRepository.findByUserIdAndPrice(1, 10.02)).thenReturn(minStockPrice);
			     assertEquals(10.02, stockPrice.getPrice());
		    }
		
}
