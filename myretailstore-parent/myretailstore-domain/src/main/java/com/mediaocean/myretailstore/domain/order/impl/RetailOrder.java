package com.mediaocean.myretailstore.domain.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.mediaocean.myretailstore.domain.order.Order;
import com.mediaocean.myretailstore.domain.order.OrderSummary;
import com.mediaocean.myretailstore.domain.order.dal.impl.RetailOrderDao;

public class RetailOrder implements Order {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetailOrder.class);
	
	private final RetailOrderDao retailOrderDao;
	
	public RetailOrder(RetailOrderDao retailOrderDao) {
		this.retailOrderDao = retailOrderDao;
	}

	private OrderSummary orderSummary;
	private List<RetailOrderItem> retailOrderItemList = new ArrayList<RetailOrderItem>();
	
	public OrderSummary getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(OrderSummary orderSummary) {
		this.orderSummary = orderSummary;
	}

	public List<RetailOrderItem> getRetailOrderItemList() {
		return retailOrderItemList;
	}

	public void addRetailOrderItem(RetailOrderItem retailOrderItem) {
		this.retailOrderItemList.add(retailOrderItem);
	}

	@Override
	public void modify() {
		Assert.notNull(this.orderSummary, "Order Summary can't be null");
		
		// Calculation of item wise amount, total amount etc.
		BigDecimal totalAmount = new BigDecimal(0);
		for (RetailOrderItem item : retailOrderItemList) {
			// Calculate amount
			item.setAmount(calculateAmount(item));
			
			// Add item wise amount to total amount
			totalAmount = totalAmount.add(item.getAmount());
		}
		orderSummary.setOrderAmount(totalAmount);
		
		// Update DB
		retailOrderDao.modify(this);
	}
	
	private BigDecimal calculateAmount(RetailOrderItem item) {
		Double itemPrice = item.getProduct().getPrice();
		int itemQty = item.getQuantity();

		BigDecimal amount;
		Integer itemServiceTax = item.getProduct().getServiceTax();
		if (itemServiceTax == null) {
			amount = new BigDecimal(itemPrice * itemQty);
		} else {
			amount = new BigDecimal( Double.valueOf(itemPrice * itemQty) + (itemPrice * itemQty * (itemServiceTax / 100d)));
		}
		
		LOGGER.debug("Amount calculated for Product Code : {} is -> {}", item.getProduct().getCode(), amount);
		return amount;
	}
	
	public static void main(String args[]) {
		BigDecimal amount = new BigDecimal( Double.valueOf(50 * 25) + Double.valueOf(50 * 25 * (10/100d)));
		System.out.println(amount);
	}

}
