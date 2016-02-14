package com.mediaocean.myretailstore.domain.common;

public class Customer {

	private Long id;
	private String name;

	public Customer(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
