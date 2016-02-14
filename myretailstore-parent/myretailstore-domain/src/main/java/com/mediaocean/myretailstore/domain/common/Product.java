package com.mediaocean.myretailstore.domain.common;

public class Product {

	private String code;
	private String name;
	private String description;
	private ProductCategory category;
	private Integer serviceTax;
	private Double price;

	public Product(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
		this.serviceTax = category.getDefaultTax();
	}

	public Integer getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(Integer serviceTax) {
		this.serviceTax = serviceTax;
	}

	public String getCode() {
		return code;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", category=");
		builder.append(category);
		builder.append(", serviceTax=");
		builder.append(serviceTax);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}

}
