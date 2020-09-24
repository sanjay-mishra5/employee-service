package com.tesco.finance.corestockvaluation.domain;



import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class StockPayload {
	private String location;
	private String item;
	private String headline;
	private String clarifyingNote;
	private String transactionDate;
	@JsonIgnore
	private Exception deSerializerException;
}