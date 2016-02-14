package com.mediaocean.myretailstore.cart.impl;

import java.util.ArrayList;
import java.util.List;

import com.mediaocean.myretailstore.cart.CartDto;
import com.mediaocean.myretailstore.cart.CartItemDto;
import com.mediaocean.myretailstore.common.Transformer;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrder;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrderItem;

public class CartDtoTransformer implements Transformer<RetailOrder, CartDto> {

	@Override
	public CartDto transform(RetailOrder retailOrder) {
		CartDto cart = new CartDto(retailOrder.getOrderSummary().getOrderId());
		
		List<CartItemDto> itemList = new ArrayList<CartItemDto>();
		for (RetailOrderItem item : retailOrder.getRetailOrderItemList()) {
			CartItemDto cartItem = new CartItemDto();
			cartItem.setCode(item.getProduct().getCode());
			cartItem.setQuantity(item.getQuantity());
			
			itemList.add(cartItem);
		}
		cart.setItemList(itemList);
		
		return cart;
	}

}
