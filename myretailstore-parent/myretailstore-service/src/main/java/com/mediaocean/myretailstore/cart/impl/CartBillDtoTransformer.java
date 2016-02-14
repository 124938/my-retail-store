package com.mediaocean.myretailstore.cart.impl;

import com.mediaocean.myretailstore.cart.CartBillDto;
import com.mediaocean.myretailstore.cart.CartBillItemDto;
import com.mediaocean.myretailstore.cart.CartBillSummaryDto;
import com.mediaocean.myretailstore.common.Transformer;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrder;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrderItem;

public class CartBillDtoTransformer implements Transformer<RetailOrder, CartBillDto> {

	@Override
	public CartBillDto transform(RetailOrder retailOrder) {
		CartBillSummaryDto billSummary = new CartBillSummaryDto(retailOrder.getOrderSummary().getOrderId(), retailOrder.getOrderSummary().getOrderDate(), retailOrder.getOrderSummary().getOrderAmount());
		
		CartBillDto cartBill = new CartBillDto(billSummary);
		for (RetailOrderItem item : retailOrder.getRetailOrderItemList()) {
			CartBillItemDto billItem = new CartBillItemDto(item.getProduct().getName(), item.getProduct().getPrice(), item.getQuantity(), item.getProduct().getServiceTax(), item.getAmount());
			cartBill.addCartBillDetails(billItem);
		}
		
		return cartBill;
	}

}
