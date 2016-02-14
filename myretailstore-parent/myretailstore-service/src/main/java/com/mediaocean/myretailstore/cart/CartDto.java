package com.mediaocean.myretailstore.cart;

import java.util.ArrayList;
import java.util.List;

public class CartDto {
	private long id;
	private List<CartItemDto> itemList = new ArrayList<CartItemDto>();

	public CartDto() {
	}

	public CartDto(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<CartItemDto> getItemList() {
		return itemList;
	}

	public void setItemList(List<CartItemDto> itemList) {
		this.itemList = itemList;
	}

}
