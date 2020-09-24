package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tesco.finance.corestockvaluation.domain.StockPayload;

public class StockPayloadDeserializer implements Deserializer<StockPayload> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockPayloadDeserializer.class);
	@Override
	public StockPayload deserialize(String args, byte[] devBytes) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		StockPayload stockPayload = null;
		try {
			stockPayload = mapper.readValue(devBytes, StockPayload.class);
		} catch (JsonParseException e) {

			LOGGER.error("Parsing Error for event "+devBytes.toString()+e.getMessage());
			stockPayload = StockPayload.builder().deSerializerException(e).build();
			return stockPayload;

		} catch (Exception e) {
			LOGGER.error("Error while deserializing the EnrichedEventInfo - object[{}]",e);
			stockPayload = StockPayload.builder().deSerializerException(e).build();
			return stockPayload;
		}
		return stockPayload;
	}

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {

	}

}
