package com.mediaocean.myretailstore.cart;

import java.math.BigDecimal;

public class CartBillItemDto {

	private final String name;
	private final Double price;
	private final int qty;
	private final Integer serviceTax;
	private final BigDecimal amount;
	
	public CartBillItemDto(String name, Double price, int qty, Integer serviceTax, BigDecimal amount) {
		this.name = name;
		this.price = price;
		this.qty = qty;
		this.serviceTax = serviceTax;
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public int getQty() {
		return qty;
	}

	public Integer getServiceTax() {
		return serviceTax;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Double getPrice() {
		return price;
	}
	
	
}
