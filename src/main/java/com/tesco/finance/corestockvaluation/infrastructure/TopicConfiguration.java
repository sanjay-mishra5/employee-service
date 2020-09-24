package com.tesco.finance.corestockvaluation.infrastructure;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

//@Component
public class TopicConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopicConfiguration.class);
	
	@Value("${kafka.stock.inbound.topic}")
	private String stockEvent;
	
	@Bean
	public KafkaAdmin admin(@Qualifier("kafkaConfigMap") Map<String,Object> configs) {

	    return new KafkaAdmin(configs);
	}

//	@Bean
//	public NewTopic topic1() {
//	    return TopicBuilder.name(stockEvent)
//	            .partitions(3)
//	            .replicas(3)
//	            .compact()
//	            .build();
//	}

	
	
//	@Bean
//	public NewTopic topic2() {
//	    return TopicBuilder.name("thing2")
//	            .partitions(10)
//	            .replicas(3)
//	            .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
//	            .build();
//	}

	
}