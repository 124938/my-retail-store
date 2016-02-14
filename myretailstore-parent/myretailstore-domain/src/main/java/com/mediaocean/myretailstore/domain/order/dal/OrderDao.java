package com.mediaocean.myretailstore.domain.order.dal;

import com.mediaocean.myretailstore.domain.order.Order;

public interface OrderDao<T extends Order> {
	
	void modify(T order);
	
}
