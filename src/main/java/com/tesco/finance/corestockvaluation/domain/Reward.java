package com.tesco.finance.corestockvaluation.domain;

import java.time.ZonedDateTime;

public class Reward {

	private String gtin;
	private String stockKeepingUnit;
	private Long apportionedDiscount;
	private String promotionId;
	private String promotionType;

	private ZonedDateTime paidDate;

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public Long getApportionedDiscount() {
		return apportionedDiscount;
	}

	public void setApportionedDiscount(Long apportionedDiscount) {
		this.apportionedDiscount = apportionedDiscount;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getStockKeepingUnit() {
		return stockKeepingUnit;
	}

	public void setStockKeepingUnit(String stockKeepingUnit) {
		this.stockKeepingUnit = stockKeepingUnit;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public ZonedDateTime getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(ZonedDateTime paidDate) {
		this.paidDate = paidDate;
	}

	@Override
	public String toString() {
		return "Reward [gtin=" + gtin + ", stockKeepingUnit=" + stockKeepingUnit + ", apportionedDiscount="
				+ apportionedDiscount + ", promotionId=" + promotionId + ", promotionType=" + promotionType
				+ ", paidDate=" + paidDate + "]";
	}

}
