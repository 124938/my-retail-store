package com.mediaocean.myretailstore.cart.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mediaocean.myretailstore.cart.CartBillDto;
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
		
		// Fetch existing cart details using cart identification i.e. cart id
		return fetchCart(cartId);
	}
	
	private CartDto fetchCart(long cartId) {
		// Fetch order based upon cart id i.e. order id
		LOGGER.debug("Cart Details to be fetched for cart Id -> {}", cartId);
		Order order = orderRepository.fetchOrder(cartId);
		
		// Prepare DTO to send to client
		if (order instanceof RetailOrder) {
			RetailOrder retailOrder = (RetailOrder) order;

			// Transform Retail Order to Cart DTO
			return new CartDtoTransformer().transform(retailOrder);
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
			// Fetch product details from product repository
			Product newProduct = productRepository.fetchProductDetails(cartItem.getCode());
			
			// Prepare Retail Order Item
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
			return new CartBillDtoTransformer().transform(retailOrder);
		} else {
			throw new RuntimeException("As of now only retail order has been supported by system");
		}
	}
	
}
