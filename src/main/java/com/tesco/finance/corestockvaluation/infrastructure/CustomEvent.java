package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.tesco.finance.corestockvaluation.domain.StockInfo;

public class CustomEvent extends ApplicationEvent {
	List<StockInfo> stockinfo;

	public CustomEvent(Object source, List<StockInfo> stockinfo) {
		super(source);
		this.stockinfo = stockinfo;
	}

	public List<StockInfo> getStockinfo() {
		return stockinfo;
	}
	



}