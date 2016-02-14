package com.mediaocean.myretailstore.domain.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.mediaocean.myretailstore.domain.order.Order;
import com.mediaocean.myretailstore.domain.order.OrderSummary;
import com.mediaocean.myretailstore.domain.order.dal.impl.RetailOrderDao;

public class RetailOrder implements Order {
	
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
			BigDecimal amount;
			if (item.getProduct().getServiceTax() == null) {
				amount = new BigDecimal(item.getProduct().getPrice() * item.getQuantity());
			} else {
				amount = new BigDecimal( (item.getProduct().getPrice() * item.getQuantity()) + ( item.getProduct().getPrice() * (item.getProduct().getServiceTax() / 100)));
			}
			item.setAmount(amount);
			
			// Add item wise amount to total amount
			totalAmount = totalAmount.add(item.getAmount());
		}
		orderSummary.setOrderAmount(totalAmount);
		
		// Update DB
		retailOrderDao.modify(this);
	}

}
