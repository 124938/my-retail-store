package com.mediaocean.myretailstore.domain.common;

public enum ProductCategory {
	A(10),
	B(20),
	C(null);
	
	private Integer defaultTax;
	
	private ProductCategory(Integer defaultTax) {
		this.defaultTax = defaultTax;
	}
	
	public Integer getDefaultTax() {
		return this.defaultTax;
	}
}
