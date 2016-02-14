package com.mediaocean.myretailstore.domain.common.dal;

import com.mediaocean.myretailstore.domain.common.Product;

public interface ProductDao {
	
	Product fetch(String productCode);
}
