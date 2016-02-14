package com.mediaocean.myretailstore.domain.order.impl;

import org.springframework.util.Assert;

import com.mediaocean.myretailstore.domain.order.Order;
import com.mediaocean.myretailstore.domain.order.OrderRepository;
import com.mediaocean.myretailstore.domain.order.OrderSummary;
import com.mediaocean.myretailstore.domain.order.dal.CommonOrderDao;

public class DefaultOrderRepository implements OrderRepository {
	
	private final CommonOrderDao commonOrderDao;
	
	public DefaultOrderRepository(CommonOrderDao commonOrderDao) {
		this.commonOrderDao = commonOrderDao;
	}

	@Override
	public OrderSummary createNewOrder() {
		// Create new order summary
		return commonOrderDao.create();
	}
	
	@Override
	public Order fetchOrder(long orderId) {
		// Fetch Order from DB
		Order order = commonOrderDao.fetch(orderId);
		
		// Validation
		Assert.notNull(order, "Order does not exist for order id -> "+orderId);
		
		return order;
	}

}
