package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.couchbase.client.core.json.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tesco.finance.corestockvaluation.domain.StockInfo;

@Component
public class CustomEventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomEventListener.class);

	@Value("${kafka.svs.wac.input.topic}")
	private String svsWACTopic;

	@Autowired
	@Qualifier("producerTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private CustomEventPublisher customEventPublisher;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onApplicationEvent(CustomEvent event) {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			for (StockInfo item : event.getStockinfo()) {

				// stockPayload = Mapper.(devBytes, StockPayload.class);
				JSONObject obj = new JSONObject();

				kafkaTemplate.send(svsWACTopic, mapper.writeValueAsString(item));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}