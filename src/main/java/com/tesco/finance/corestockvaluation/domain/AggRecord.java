package com.tesco.finance.corestockvaluation.domain;

import java.time.ZonedDateTime;

public interface AggRecord {

	String getStore_id();

	String getPaid_date();

	String getStock_keeping_unit();

	String getFinance_country();

	Long getRetail_price();

	Long getQty();

}
