package com.mediaocean.myretailstore.cart.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.transport.local.LocalConduit;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.mediaocean.myretailstore.cart.CartDto;
import com.mediaocean.myretailstore.cart.CartItemDto;
import com.mediaocean.myretailstore.cart.CartService;
import com.mediaocean.myretailstore.domain.common.Product;
import com.mediaocean.myretailstore.domain.common.ProductCategory;
import com.mediaocean.myretailstore.domain.common.ProductRepository;
import com.mediaocean.myretailstore.domain.order.OrderRepository;
import com.mediaocean.myretailstore.domain.order.OrderStatus;
import com.mediaocean.myretailstore.domain.order.OrderSummary;
import com.mediaocean.myretailstore.domain.order.dal.impl.RetailOrderDao;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrder;
import com.mediaocean.myretailstore.domain.order.impl.RetailOrderItem;

public class DefaultCartServiceTest {

	private final static String ENDPOINT_ADDRESS = "local://cart";
	
	private OrderRepository orderRepositoryMock;
	private ProductRepository productRepositoryMock;
	private RetailOrderDao retailOrderDaoMock;
	
	private Server server;

	@Before
	public void initialize() throws Exception {
		orderRepositoryMock = EasyMock.createMock(OrderRepository.class);
		productRepositoryMock = EasyMock.createMock(ProductRepository.class);
		retailOrderDaoMock = EasyMock.createMock(RetailOrderDao.class);

		// Create resource provider
		SingletonResourceProvider resourceProvider = new SingletonResourceProvider(new DefaultCartService(orderRepositoryMock, productRepositoryMock), true);

		// Create object mapper
		ObjectMapper mapper = new ObjectMapper();
	    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		// Create provider list
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJsonProvider(mapper));
		
		// add custom providers if any
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(CartService.class);
		sf.setProviders(providers);
		sf.setResourceProvider(CartService.class, resourceProvider);
		sf.setAddress(ENDPOINT_ADDRESS);

		server = sf.create();
	}

	@After
	public void destroy() throws Exception {
		orderRepositoryMock = null;
		productRepositoryMock = null;
		
		server.stop();
		server.destroy();
	}
	
	private WebClient prepareWebClient() {
		WebClient client = WebClient.create(ENDPOINT_ADDRESS, Collections.singletonList(new JacksonJsonProvider()));
		WebClient.getConfig(client).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
		WebClient.getConfig(client).getInInterceptors().add(new LoggingInInterceptor());
		WebClient.getConfig(client).getOutInterceptors().add(new LoggingOutInterceptor());
		
		return client;
	}
	
	@Test
	public void createNewCartIdTest() {
		EasyMock.expect(orderRepositoryMock.createNewOrder()).andReturn(prepareMockOrderSummary(System.currentTimeMillis(), new Date(), OrderStatus.CREATED));
		EasyMock.replay(orderRepositoryMock);
		
		WebClient client = prepareWebClient();
		client.accept(MediaType.APPLICATION_JSON_TYPE);
		client.path("new");
		
		Long cartId = client.get(Long.class);
		Assert.assertNotNull(cartId);
		
		EasyMock.verify(orderRepositoryMock);
	}
	
	@Test
	public void fetchCartDetailsTest() {
		long orderId = System.currentTimeMillis();
		
		EasyMock.expect(orderRepositoryMock.fetchOrder(orderId)).andReturn(prepareMockRetailOrder(orderId));
		EasyMock.replay(orderRepositoryMock);
		
		WebClient client = prepareWebClient();
		client.accept(MediaType.APPLICATION_JSON_TYPE);
		client.path(orderId);
		
		CartDto cart = client.get(CartDto.class);
		Assert.assertNotNull(cart);
		Assert.assertEquals(2, cart.getItemList().size());
		
		EasyMock.verify(orderRepositoryMock);
	}
	
	@Test
	public void updateCartItemTest() {
		long orderId = System.currentTimeMillis();

		RetailOrder mockOrder = prepareMockRetailOrder(orderId);
		EasyMock.expect(orderRepositoryMock.fetchOrder(orderId)).andReturn(mockOrder);
		EasyMock.replay(orderRepositoryMock);

		String productCode = "PRD-007";
		EasyMock.expect(productRepositoryMock.fetchProductDetails(productCode)).andReturn(prepareMockProduct(productCode, "test", "test", ProductCategory.B, 100d));
		EasyMock.replay(productRepositoryMock);
		
		retailOrderDaoMock.modify(mockOrder);
		EasyMock.expectLastCall();
		EasyMock.replay(retailOrderDaoMock);
		
		WebClient client = prepareWebClient();
		client.type(MediaType.APPLICATION_JSON_TYPE);
		client.path(orderId + "/update");
		
		CartItemDto cartItem = new CartItemDto();
		cartItem.setCode("PRD-007");
		cartItem.setQuantity(10);
		
		Response response = client.post(cartItem);
		Assert.assertNotNull(response);
		
		EasyMock.verify(orderRepositoryMock);
		EasyMock.verify(productRepositoryMock);
		EasyMock.verify(retailOrderDaoMock);
	}
	
	private OrderSummary prepareMockOrderSummary(long orderId, Date orderDate, OrderStatus orderStatus) {
		OrderSummary os = new OrderSummary(orderId);
		os.setOrderDate(orderDate);
		os.setOrderStatus(orderStatus);
		
		return os;
	}
	
	private Product prepareMockProduct(String code, String name, String description, ProductCategory category, Double price) {
		Product product = new Product(code);
		product.setName(name);
		product.setDescription(description);
		product.setCategory(category);
		product.setPrice(price);
		
		return product;
	}
	
	private RetailOrderItem prepareMockRetailOrderItem(String code, String name, String description, ProductCategory category, Double price, int quantity) {
		RetailOrderItem item = new RetailOrderItem();
		item.setProduct(prepareMockProduct(code, name, description, category, price));
		item.setQuantity(quantity);
		item.setAmount(new BigDecimal(2020));
		
		return item;
	}
	
	private RetailOrder prepareMockRetailOrder(long orderId) {
		RetailOrder order = new RetailOrder(retailOrderDaoMock);
		order.setOrderSummary(prepareMockOrderSummary(orderId, new Date(), OrderStatus.CREATED));
		order.addRetailOrderItem(prepareMockRetailOrderItem("JUnit - 007", "JUnit - 007 - Name", "JUnit - 007 - desc", ProductCategory.A, 200d, 10));
		order.addRetailOrderItem(prepareMockRetailOrderItem("JUnit - 008", "JUnit - 008 - Name", "JUnit - 008 - desc", ProductCategory.B, 100d, 20));
		
		return order;
	}

}
