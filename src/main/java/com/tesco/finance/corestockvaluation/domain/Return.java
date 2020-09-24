package com.tesco.finance.corestockvaluation.domain;

public class Return {

	private String stockKeepingUnit;
	private String gtin;
	private Long priceAfterDiscounts;
	private Long quantity;
	private String unitOfMeasure;

	private String reason;

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public Long getPriceAfterDiscounts() {
		return priceAfterDiscounts;
	}

	public void setPriceAfterDiscounts(Long priceAfterDiscounts) {
		this.priceAfterDiscounts = priceAfterDiscounts;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStockKeepingUnit() {
		return stockKeepingUnit;
	}

	public void setStockKeepingUnit(String stockKeepingUnit) {
		this.stockKeepingUnit = stockKeepingUnit;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Return [stockKeepingUnit=" + stockKeepingUnit + ", gtin=" + gtin + ", priceAfterDiscounts="
				+ priceAfterDiscounts + ", quantity=" + quantity + ", unitOfMeasure=" + unitOfMeasure + ", reason="
				+ reason + "]";
	}

}
