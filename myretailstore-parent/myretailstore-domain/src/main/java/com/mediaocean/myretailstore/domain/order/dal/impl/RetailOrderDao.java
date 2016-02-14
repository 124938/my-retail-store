package com.mediaocean.myretailstore.domain.order.dal.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mediaocean.myretailstore.domain.order.dal.CommonOrderDao;
import com.mediaocean.myretailstore.domain.order.dal.OrderDao;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrder;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrderItem;

public class RetailOrderDao implements OrderDao<RetailOrder> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetailOrderDao.class);
	
	private final CommonOrderDao commonOrderDao;
	
	public RetailOrderDao(CommonOrderDao commonOrderDao) {
		this.commonOrderDao = commonOrderDao;
	}

	@Override
	public void modify(RetailOrder order) {
		// TODO : Required to execute Update/Insert Query in ORDER_RETAIL_ITEM table
		for (RetailOrderItem retailOrderItem : order.getRetailOrderItemList()) {
			LOGGER.debug("Retail Order Item to be updated in DB is -> {}", retailOrderItem);
		}
		
		// Modify Order Summary
		commonOrderDao.modify(order.getOrderSummary());
		
		// TODO : Remove below code as soon as DB support is added to class
		commonOrderDao.store(order.getOrderSummary().getOrderId(), order);
	}

}
