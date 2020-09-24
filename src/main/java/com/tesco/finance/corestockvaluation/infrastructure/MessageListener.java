package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.uuid.Generators;
import com.tesco.finance.corestockvaluation.domain.EventInfo;
import com.tesco.finance.corestockvaluation.domain.STSInfo;
import com.tesco.finance.corestockvaluation.domain.EventPayload;
import com.tesco.finance.corestockvaluation.domain.StockInfo;
import com.tesco.finance.corestockvaluation.domain.StockPayload;
import com.tesco.finance.corestockvaluation.persistence.EventInfoRepository;
import com.tesco.finance.corestockvaluation.persistence.StockInfoRepository;
import com.tesco.finance.corestockvaluation.service.EnrichService;
import com.tesco.finance.corestockvaluation.service.StockInfoInsertService;

@Component
public class MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

	@Autowired
	private StockInfoRepository stockInfoRepository;
	@Autowired
	private CustomEventPublisher customEventPublisher;

	@Autowired
	private EventInfoRepository stsInfoRepository;

	@Autowired
	private EnrichService enrichService;

	@Value("${kafka.svs.wac.input.topic}")
	private String svsWACTopic;

	@Autowired
	@Qualifier("producerTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaConfiguration kafkaTransactionConfiguration;
	private static int CountUnique = 9001;

	/*
	 * @KafkaListener(id = "${kafka.svs.inbound.event.topic}.id", topics =
	 * "${kafka.svs.inbound.event.topic}", clientIdPrefix =
	 * "#{T( java.net.InetAddress).getLocalHost().getHostAddress() }", groupId =
	 * "${kafka.svs.inbound.event.topic}-group-id", autoStartup =
	 * "${listen.auto.start:true}", concurrency = "${listen.concurrency:1}",
	 * containerFactory = "kafkaListenerContainerFactory")
	 * 
	 * @Transactional(transactionManager = "chainedKafkaTransactionManager",
	 * propagation = Propagation.REQUIRED) public void
	 * listenToStockUpdateEvent(@Payload List<StockPayload> StockPayload,
	 * // @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
	 * 
	 * @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
	 * 
	 * @Header(KafkaHeaders.RECEIVED_TOPIC) String
	 * topic, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
	 * 
	 * @Header(KafkaHeaders.OFFSET) long offset
	 * 
	 * ) {
	 * 
	 * System.out.println("Entered Listner");
	 * 
	 * for (StockPayload payload : StockPayload) { if
	 * (payload.getDeSerializerException() == null) {
	 * 
	 * StockInfo stockInfo = new StockInfo();
	 * 
	 * stockInfo.setLocation(payload.getLocation());
	 * stockInfo.setClarifyingNote(payload.getClarifyingNote());
	 * stockInfo.setHeadline(payload.getHeadline());
	 * stockInfo.setItem(payload.getItem());
	 * stockInfo.setTransactionDate(payload.getTransactionDate());
	 * 
	 * enrichedStockInfo.add(stockInfo);
	 * 
	 * }
	 * 
	 * // customEventPublisher.publish(stockInfo.getId());
	 * 
	 * }
	 * 
	 * System.out.println("leaving Listner");
	 * stockInfoRepository.saveAll(enrichedStockInfo);
	 * 
	 * }
	 */

	@KafkaListener(id = "${kafka.svs.inbound.event.topic}.id", topics = "${kafka.svs.inbound.event.topic}", autoStartup = "${listen.auto.start:true}", containerFactory = "kafkaBatchListenerContainerFactory")

	@Transactional(transactionManager = "chainedKafkaTransactionManager")
	public void listenAsBatch(@Payload List<EventPayload> payloadList,

			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) List<Long> ts,
			@Header(KafkaHeaders.OFFSET) List<Long> offset

	) throws Exception {

		LOGGER.info("Beginning to consume batch messages" + payloadList.size());

		List<EventInfo> enrichedSTSInfo = new ArrayList<EventInfo>();
		for (EventPayload e : payloadList) {

			enrichedSTSInfo.add(enrichService.performAction(e));

		}
		stsInfoRepository.save(enrichedSTSInfo);
		
		LOGGER.info("***************Leaving  consume batch messages*******" );

	}

	/*
	 * @KafkaListener(id = "${kafka.svs.wac.input.topic}.id", topics =
	 * "${kafka.svs.wac.input.topic}", autoStartup = "${listen.auto.start:false}",
	 * containerFactory = "kafkaBatchListenerContainerFactorySale")
	 * 
	 * @Transactional(transactionManager = "chainedKafkaTransactionManager") public
	 * void listenAsBatchSaleConsolidation(@Payload List<SaleConsolidationPayload>
	 * payloadList,
	 * 
	 * @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partition,
	 * 
	 * @Header(KafkaHeaders.RECEIVED_TOPIC) String
	 * topic, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) List<Long> ts,
	 * 
	 * @Header(KafkaHeaders.OFFSET) List<Long> offset, Acknowledgment ack) throws
	 * Exception {
	 * 
	 * LOGGER.info("Beginning to consume batch messages" + payloadList.size());
	 * List<SaleConsolidation> enrichedSaleConsolidation = new
	 * ArrayList<SaleConsolidation>();
	 * 
	 * for (SaleConsolidationPayload e : payloadList) { SaleConsolidation
	 * saleConsolidationobj = new SaleConsolidation();
	 * saleConsolidationobj.setLiKey(e.getLiKey());
	 * saleConsolidationobj.setLconactItem(e.getLconactItem());
	 * enrichedSaleConsolidation.add(saleConsolidationobj);
	 * 
	 * }
	 * 
	 * List<SaleConsolidation> al = bulkInsertService
	 * .SalesConsolidationbulkWithEntityManager(enrichedSaleConsolidation);
	 * 
	 * LOGGER.info("All batch messages consumed" + al.size());
	 * 
	 * }
	 */
}