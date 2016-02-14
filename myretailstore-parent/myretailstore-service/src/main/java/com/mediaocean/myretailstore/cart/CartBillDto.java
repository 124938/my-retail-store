package com.mediaocean.myretailstore.cart;

import java.util.ArrayList;
import java.util.List;

public class CartBillDto {
	private CartBillSummaryDto billSummary;
	private List<CartBillItemDto> billDetailsList = new ArrayList<CartBillItemDto>();

	public CartBillDto(CartBillSummaryDto billSummary) {
		this.billSummary = billSummary;
	}

	public void addCartBillDetails(CartBillItemDto billDetails) {
		this.billDetailsList.add(billDetails);
	}

	public CartBillSummaryDto getBillSummary() {
		return billSummary;
	}

	public List<CartBillItemDto> getBillDetailsList() {
		return billDetailsList;
	}

}
