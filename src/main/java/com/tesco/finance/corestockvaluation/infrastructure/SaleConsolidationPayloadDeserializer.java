package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tesco.finance.corestockvaluation.domain.SaleConsolidationPayload;

public class SaleConsolidationPayloadDeserializer implements Deserializer<SaleConsolidationPayload> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaleConsolidationPayloadDeserializer.class);
	@Override
	public SaleConsolidationPayload deserialize(String args, byte[] devBytes) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		SaleConsolidationPayload saleConsolidationPayload = null;
		try {
			saleConsolidationPayload = mapper.readValue(devBytes, SaleConsolidationPayload.class);
		} catch (JsonParseException e) {

			LOGGER.error("Parsing Error for event "+devBytes.toString()+e.getMessage());
			saleConsolidationPayload = SaleConsolidationPayload.builder().deSerializerException(e).build();
			return saleConsolidationPayload;

		} catch (Exception e) {
			LOGGER.error("Error while deserializing the EnrichedEventInfo - object[{}]",e);
			saleConsolidationPayload = SaleConsolidationPayload.builder().deSerializerException(e).build();
			return saleConsolidationPayload;
		}
		return saleConsolidationPayload;
	}

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {

	}

}
