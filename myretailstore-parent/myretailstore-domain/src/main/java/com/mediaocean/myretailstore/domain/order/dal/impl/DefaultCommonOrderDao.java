package com.mediaocean.myretailstore.domain.order.dal.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.mediaocean.myretailstore.domain.order.Order;
import com.mediaocean.myretailstore.domain.order.OrderStatus;
import com.mediaocean.myretailstore.domain.order.OrderSummary;
import com.mediaocean.myretailstore.domain.order.dal.CommonOrderDao;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrder;

public class DefaultCommonOrderDao implements CommonOrderDao {
	
	private static final String RETAIL_ORDER_BEAN_ID = "retailOrder";
	
	private Map<Long, Order> orderMap = new ConcurrentHashMap<Long, Order>();
	
	@Autowired
	private ApplicationContext applicationContext;

	private RetailOrder getOrderBean() {
		return (RetailOrder) applicationContext.getBean(RETAIL_ORDER_BEAN_ID);		
	}
	
	@Override
	public OrderSummary create() {
		OrderSummary orderSummary = new OrderSummary(System.currentTimeMillis());
		orderSummary.setOrderDate(new Date());
		orderSummary.setOrderStatus(OrderStatus.CREATED);
		
		// TODO : Instead of DB query, using Map to store order against order id
		RetailOrder retailOrder = getOrderBean();
		retailOrder.setOrderSummary(orderSummary);
		
		store(orderSummary.getOrderId(), retailOrder);
		
		return orderSummary;
	}

	@Override
	public void modify(OrderSummary orderSummary) {
		// TODO - Required to execute update query
	}

	@Override
	public Order fetch(long orderId) {
		return orderMap.get(orderId);
	}

	@Override
	public void store(long orderId, Order order) {
		orderMap.put(orderId, order);
	}

}
