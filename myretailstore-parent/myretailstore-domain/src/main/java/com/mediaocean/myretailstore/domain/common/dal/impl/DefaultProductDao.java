package com.mediaocean.myretailstore.domain.common.dal.impl;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mediaocean.myretailstore.domain.common.Product;
import com.mediaocean.myretailstore.domain.common.dal.ProductDao;

public class DefaultProductDao implements ProductDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDao.class);
	
	// TODO - Product Map should get removed as soon as product data is getting fetched from PRODUCT table
	private Map<String, Product> productMap = new ConcurrentHashMap<String, Product>();

	@Override
	public Product fetch(String productCode) {
		// TODO - Below code should get replaced by SELECT query
		Product product = new Product(productCode);
		product.setName("Name - "+productCode);
		product.setDescription("Description - "+productCode);
		product.setServiceTax(new Random(10).nextFloat() + 10);
		product.setPrice(new Random(5000).nextDouble() + 500);
		
		// Put product into Map
		productMap.put(productCode, product);
		LOGGER.debug("Product -> "+product);
		
		return productMap.get(productCode);
	}

}
