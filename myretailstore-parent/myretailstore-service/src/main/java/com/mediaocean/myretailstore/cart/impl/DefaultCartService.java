package com.mediaocean.myretailstore.cart.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mediaocean.myretailstore.cart.CartBillDto;
import com.mediaocean.myretailstore.cart.CartBillItemDto;
import com.mediaocean.myretailstore.cart.CartBillSummaryDto;
import com.mediaocean.myretailstore.cart.CartDto;
import com.mediaocean.myretailstore.cart.CartItemDto;
import com.mediaocean.myretailstore.cart.CartService;
import com.mediaocean.myretailstore.domain.common.Product;
import com.mediaocean.myretailstore.domain.common.ProductRepository;
import com.mediaocean.myretailstore.domain.order.Order;
import com.mediaocean.myretailstore.domain.order.OrderRepository;
import com.mediaocean.myretailstore.domain.order.OrderSummary;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrder;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrderItem;

public class DefaultCartService implements CartService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCartService.class);
	
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	
	public DefaultCartService(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	@Override
	public long createNewCartId() {
		// Create new blank order in DB
		OrderSummary orderSummary = orderRepository.createNewOrder();
		
		// Return order id, which should be considered as cart identification
		return orderSummary.getOrderId();
	}

	@Override
	public CartDto fetchCartDetails(long cartId) {
		LOGGER.debug("Cart Id received from client is -> {}", cartId);
		
		return fetchCart(cartId);
	}
	
	private CartDto fetchCart(long cartId) {
		// Fetch order based upon cart id i.e. order id
		Order order = orderRepository.fetchOrder(cartId);
		
		// Prepare DTO to send to client
		if (order instanceof RetailOrder) {
			RetailOrder retailOrder = (RetailOrder) order;
			
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
		} else {
			throw new RuntimeException("As of now only retail order has been supported by system");
		}
	}

	@Override
	public void updateCartItem(long cartId, CartItemDto cartItem) {
		// Fetch order based upon cart id i.e. order id
		Order order = orderRepository.fetchOrder(cartId);

		// Prepare DTO to send to client
		if (order instanceof RetailOrder) {
			RetailOrder retailOrder = (RetailOrder) order;
			
			// Update Retail Order domain object
			updateRetailOrder(retailOrder, cartItem);
			
			// Modify DB
			retailOrder.modify();
		} else {
			throw new RuntimeException("As of now only retail order has been supported by system");
		}
		
	}
	
	private void updateRetailOrder(RetailOrder retailOrder, CartItemDto cartItem) {
		boolean isProductPresent = false;
		for (RetailOrderItem item : retailOrder.getRetailOrderItemList()) {
			if (item.getProduct().getCode().equals(cartItem.getCode())) {
				item.setQuantity(cartItem.getQuantity());
				isProductPresent = true;
			}
		}
		
		if (!isProductPresent) {
			Product newProduct = productRepository.fetchProductDetails(cartItem.getCode());
			RetailOrderItem item = new RetailOrderItem();
			item.setProduct(newProduct);
			item.setQuantity(cartItem.getQuantity());
			
			retailOrder.addRetailOrderItem(item);
		}
	}

	@Override
	public CartBillDto checkoutCart(long cartId) {
		// Fetch order based upon cart id i.e. order id
		Order order = orderRepository.fetchOrder(cartId);

		// Prepare DTO to send to client
		if (order instanceof RetailOrder) {
			RetailOrder retailOrder = (RetailOrder) order;
			
			// Prepare Cart Bill DTO
			return generateBill(retailOrder);
		} else {
			throw new RuntimeException("As of now only retail order has been supported by system");
		}
	}
	
	private CartBillDto generateBill(RetailOrder retailOrder) {
		CartBillSummaryDto billSummary = new CartBillSummaryDto(retailOrder.getOrderSummary().getOrderId(), retailOrder.getOrderSummary().getOrderDate(), retailOrder.getOrderSummary().getOrderAmount());
		
		CartBillDto cartBill = new CartBillDto(billSummary);
		for (RetailOrderItem item : retailOrder.getRetailOrderItemList()) {
			CartBillItemDto billItem = new CartBillItemDto(item.getProduct().getName(), item.getProduct().getPrice(), item.getQuantity(), item.getProduct().getServiceTax(), item.getAmount());
			cartBill.addCartBillDetails(billItem);
		}
		
		return cartBill;
	}

}
