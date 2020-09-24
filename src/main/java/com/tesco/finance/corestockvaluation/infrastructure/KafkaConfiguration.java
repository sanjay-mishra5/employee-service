package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.sql.DataSource;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.transaction.ChainedKafkaTransactionManager;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableTransactionManagement
@EnableKafka
public class KafkaConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);

	@Value("${kafka.servers}")
	private String kafkaServer;

	@Value("${kafka.svs.inbound.event.topic}")
	private String svsInboundTopic;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	// --------------------- Consumer config
	@Bean

	public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,

			ConsumerFactory<String, String> kafkaConsumerFactory,
			ChainedKafkaTransactionManager<Object, Object> chainedTM) {

		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.getContainerProperties().setTransactionManager((PlatformTransactionManager) chainedTM);

		// These attributes cann be overridden @KafkaListener annotation

		// factory.getContainerProperties().setAckMode(AckMode.MANUAL); // By setting
		// AckMode Manual you can get the reference of Ack in the listener method args.

		factory.setConsumerFactory(kafkaConsumerFactory);

		factory.setConcurrency(5);

		return factory;

	}

	/*
	 * @Bean("kafkaListenerStringFactory") public
	 * ConcurrentKafkaListenerContainerFactory<Integer, String>
	 * kafkaListenerStringContainerFactory() {
	 * ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new
	 * ConcurrentKafkaListenerContainerFactory<>();
	 * factory.setConsumerFactory(consumerFactory1());
	 * 
	 * return factory;
	 * 
	 * }
	 */
	@Bean("consumerFactory")
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(config());
	}

	@Bean("consumerFactory1")
	public ConsumerFactory<Long, String> consumerFactory1() {
		Map<String, Object> kafkaConfig = config();

		kafkaConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SaleConsolidationPayloadDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(kafkaConfig);

	}

	// --------------------- Producer Config
	@Bean("producerTemplate")
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}

	@Bean

	public ProducerFactory<String, String> producerFactory() {

		DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(config());

		producerFactory.setTransactionIdPrefix("tamil-tx");

		return producerFactory;

	}

	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager(getEntityManager().getEntityManagerFactory());
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
	}

	@Bean(name = "chainedKafkaTransactionManager")

	public ChainedKafkaTransactionManager<Object, Object> chainedTm(KafkaTransactionManager<String, String> ktm,
			JpaTransactionManager dstm) {

		return new ChainedKafkaTransactionManager<>(ktm, dstm);

	}

	@Bean

	public KafkaTransactionManager<String, String> kafkaTransactionManager(
			ProducerFactory<String, String> producerFactory) {

		KafkaTransactionManager<String, String> ktm = new KafkaTransactionManager<String, String>(producerFactory);

		ktm.setNestedTransactionAllowed(true);

		ktm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);

		return ktm;

	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaBatchListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// These attributes cann be overridden @KafkaListener annotation
		factory.getContainerProperties().setAckMode(AckMode.MANUAL); // By setting AckMode Manual you can get the
																		// reference of Ack in the listener method args.
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(5);
		factory.setBatchListener(true);
		return factory;

	}

	/*
	 * @Bean public ConcurrentKafkaListenerContainerFactory<Long, String>
	 * kafkaBatchListenerContainerFactorySale() {
	 * ConcurrentKafkaListenerContainerFactory<Long, String> factory = new
	 * ConcurrentKafkaListenerContainerFactory<>(); // These attributes cann be
	 * overridden @KafkaListener annotation
	 * factory.getContainerProperties().setAckMode(AckMode.MANUAL); // By setting
	 * AckMode Manual you can get the // reference of Ack in the listener method
	 * args. factory.setConsumerFactory(consumerFactory1());
	 * factory.setConcurrency(5); factory.setBatchListener(true); return factory;
	 * 
	 * }
	 */

	@Bean
	public DataSourceTransactionManager dstm(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean("kafkaConfigMap")
	public Map<String, Object> config() {
		Map<String, Object> configMap = new HashMap<String, Object>();
		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		configMap.put(ProducerConfig.RETRIES_CONFIG, 0);
		configMap.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		configMap.put(ProducerConfig.LINGER_MS_CONFIG, 1);

		configMap.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EventPayloadDeserializer.class);
		configMap.put(ProducerConfig.RETRIES_CONFIG, 1);
		// introduce a delay on the send to allow more messages to accumulate
		configMap.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		// automatically reset the offset to the earliest offset
		configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		configMap.put(ConsumerConfig.GROUP_ID_CONFIG, "batch");
		configMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
		configMap.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 50000);
		configMap.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1500);
		;

		return configMap;

	}



}