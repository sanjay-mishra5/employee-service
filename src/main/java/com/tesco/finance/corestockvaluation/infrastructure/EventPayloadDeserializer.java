package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tesco.finance.corestockvaluation.domain.EventPayload;

public class EventPayloadDeserializer implements Deserializer<EventPayload> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventPayloadDeserializer.class);

	@Override
	public EventPayload deserialize(String args, byte[] devBytes) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		EventPayload eventPayload = null;
		try {
			eventPayload = mapper.readValue(devBytes, EventPayload.class);
		} catch (JsonParseException e) {

			LOGGER.error("Parsing Error for event " + devBytes.toString() + e.getMessage());
			eventPayload = EventPayload.builder().deSerializerException(e).build();
			return eventPayload;

		} catch (Exception e) {
			LOGGER.error("Error while deserializing the EnrichedEventInfo - object[{}]", e);
			eventPayload = EventPayload.builder().deSerializerException(e).build();
			return eventPayload;
		}
		return eventPayload;
	}

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {

	}

}
