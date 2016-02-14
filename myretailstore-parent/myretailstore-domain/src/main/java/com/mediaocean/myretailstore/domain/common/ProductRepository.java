package com.mediaocean.myretailstore.domain.common;

public interface ProductRepository {
	
	Product fetchProductDetails(String productCode);
}
