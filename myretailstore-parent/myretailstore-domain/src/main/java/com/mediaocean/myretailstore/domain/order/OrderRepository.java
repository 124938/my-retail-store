package com.mediaocean.myretailstore.domain.order;

public interface OrderRepository {
	
	OrderSummary createNewOrder();
	
	Order fetchOrder(long orderId);
}
