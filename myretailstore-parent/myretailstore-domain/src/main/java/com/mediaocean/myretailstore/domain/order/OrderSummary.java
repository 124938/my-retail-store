package com.mediaocean.myretailstore.domain.order;

import java.math.BigDecimal;
import java.util.Date;

import com.mediaocean.myretailstore.domain.common.Customer;

public class OrderSummary {
	
	private long orderId;
	private Date orderDate;
	private OrderStatus orderStatus;
	private Customer customer;
	private BigDecimal orderAmount;

	public OrderSummary(long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public long getOrderId() {
		return orderId;
	}

}
