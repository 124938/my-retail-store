package com.mediaocean.myretailstore.domain.common.dal.impl;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mediaocean.myretailstore.domain.common.Product;
import com.mediaocean.myretailstore.domain.common.ProductCategory;
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
		product.setCategory(findRandomProductCategory());
		product.setPrice(Double.valueOf(findRandomNumber(300, 500)));
		
		// Put product into Map
		productMap.put(productCode, product);
		LOGGER.debug("Product -> "+product);
		
		return productMap.get(productCode);
	}

	private ProductCategory findRandomProductCategory() {
		int random = findRandomNumber(1, 4);
		switch (random) {
		case 1:
			return ProductCategory.A;	
		case 2:
			return ProductCategory.B;
		default:
			return ProductCategory.C;
		}
	}
	
	private int findRandomNumber(int defaultNumber, int maxNumber) {
		Random randomNumber = new Random(); 
		int random = defaultNumber;
		for (int i=0; i<10; i++) {
			random = randomNumber.nextInt(maxNumber);
		}
		LOGGER.debug("Generated Randon number is -> {}", random);
		return random;
	}
}
