package com.tesco.finance.corestockvaluation.domain;

public class EventMessage {

	private String payload;

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "EventMessage [payload=" + payload + "]";
	}
	
}
