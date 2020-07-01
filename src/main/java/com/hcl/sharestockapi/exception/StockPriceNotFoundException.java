package com.hcl.sharestockapi.exception;

public class StockPriceNotFoundException extends RuntimeException {

	public StockPriceNotFoundException() {
	}

	public StockPriceNotFoundException(String message) {
		super(message);
	}

}
