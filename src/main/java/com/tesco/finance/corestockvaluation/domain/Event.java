/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author BARATH
 *
 */

public class Event {

	private String id;
	private String traceId;
	private String type;
	private String clientId;
	private String timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", traceId=" + traceId + ", type=" + type + ", clientId=" + clientId + ", timestamp="
				+ timestamp + "]";
	}

}
