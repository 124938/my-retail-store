package com.mediaocean.myretailstore.domain.common.impl;

import org.springframework.util.Assert;

import com.mediaocean.myretailstore.domain.common.Product;
import com.mediaocean.myretailstore.domain.common.ProductRepository;
import com.mediaocean.myretailstore.domain.common.dal.ProductDao;

public class DefaultProductRepository implements ProductRepository {
	
	private final ProductDao productDao;
	
	public DefaultProductRepository(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public Product fetchProductDetails(String productCode) {
		Product product = productDao.fetch(productCode);
		Assert.notNull(product, "Product details does not exist for product code -> "+productCode);
		
		return product;
	}

}
