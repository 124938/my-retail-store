package com.mediaocean.myretailstore.domain.order.dal;

import com.mediaocean.myretailstore.domain.order.Order;
import com.mediaocean.myretailstore.domain.order.OrderSummary;

public interface CommonOrderDao {
	
	OrderSummary create();
	
	void modify(OrderSummary orderSummary);
	
	Order fetch(long orderId);
	
	void store(long orderId, Order order);
}
