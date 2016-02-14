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
	
	// TODO - Order Map should get removed as soon as Order data is getting fetched from DB
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
		
		// TODO : Required to execute INSERT query in ORDER_SUMMARY table 
		RetailOrder retailOrder = getOrderBean();
		retailOrder.setOrderSummary(orderSummary);
		
		store(orderSummary.getOrderId(), retailOrder);
		
		return orderSummary;
	}

	@Override
	public void modify(OrderSummary orderSummary) {
		// TODO - Required to execute UPDATE query in ORDER_SUMMARY table
	}

	@Override
	public Order fetch(long orderId) {
		// TODO - Required to execute SELECT query by joining ORDER_SUMMARY & ORDER_RETAIL_ITEM table
		return orderMap.get(orderId);
	}

	@Override
	// TODO - This method should get removed as soon as DB support gets added to this class
	public void store(long orderId, Order order) {
		orderMap.put(orderId, order);
	}

}
