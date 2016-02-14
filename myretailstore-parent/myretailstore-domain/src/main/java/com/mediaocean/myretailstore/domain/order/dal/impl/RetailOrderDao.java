package com.mediaocean.myretailstore.domain.order.dal.impl;

import com.mediaocean.myretailstore.domain.order.dal.CommonOrderDao;
import com.mediaocean.myretailstore.domain.order.dal.OrderDao;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrder;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrderItem;

public class RetailOrderDao implements OrderDao<RetailOrder> {
	
	private final CommonOrderDao commonOrderDao;
	
	public RetailOrderDao(CommonOrderDao commonOrderDao) {
		this.commonOrderDao = commonOrderDao;
	}

	@Override
	public void modify(RetailOrder order) {
		// TODO : Update/Insert Query should be replaced for below code
		for (RetailOrderItem retailOrderItem : order.getRetailOrderItemList()) {
			System.out.println("retailOrderItem -> "+retailOrderItem);
		}
		
		// Update order summary table
		commonOrderDao.modify(order.getOrderSummary());
		
		commonOrderDao.store(order.getOrderSummary().getOrderId(), order);
	}

}
