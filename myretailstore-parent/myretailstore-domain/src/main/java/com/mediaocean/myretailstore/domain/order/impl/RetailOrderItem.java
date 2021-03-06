package com.mediaocean.myretailstore.domain.order.impl;

import java.math.BigDecimal;

import com.mediaocean.myretailstore.domain.common.Product;

public class RetailOrderItem {

	private Product product;
	private int quantity;
	private BigDecimal amount;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RetailOrderItem [product=");
		builder.append(product);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}

}
