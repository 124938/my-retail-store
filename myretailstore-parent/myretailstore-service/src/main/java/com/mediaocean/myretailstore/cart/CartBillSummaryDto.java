package com.mediaocean.myretailstore.cart;

import java.math.BigDecimal;
import java.util.Date;

public class CartBillSummaryDto {
	private final long billNo;
	private final Date billDate;
	private final BigDecimal totalAmount;
	
	public CartBillSummaryDto(long billNo, Date billDate, BigDecimal totalAmount) {
		this.billNo = billNo;
		this.billDate = billDate;
		this.totalAmount = totalAmount;
	}

	public long getBillNo() {
		return billNo;
	}

	public Date getBillDate() {
		return billDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
}
