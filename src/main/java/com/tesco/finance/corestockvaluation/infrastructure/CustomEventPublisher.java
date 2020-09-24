package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.tesco.finance.corestockvaluation.domain.StockInfo;

@Component
public class CustomEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publish(List<StockInfo> stockinfo) {
		applicationEventPublisher.publishEvent(new CustomEvent(this, stockinfo));

	}
}