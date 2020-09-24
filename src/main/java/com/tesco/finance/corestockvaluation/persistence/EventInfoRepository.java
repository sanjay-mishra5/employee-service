/**
 * 
 */
package com.tesco.finance.corestockvaluation.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tesco.finance.corestockvaluation.domain.EventInfo;

/**
 * @author BARATH
 *
 */
@Repository
public class EventInfoRepository extends SimpleJpaRepository<EventInfo, String> {


	private EntityManager entityManager;

	public EventInfoRepository(EntityManager entityManager) {
		super(EventInfo.class, entityManager);
		this.entityManager = entityManager;
	}

	@Transactional(transactionManager = "chainedKafkaTransactionManager")
	public List<EventInfo> save(List<EventInfo> items) {
		items.forEach(item -> entityManager.persist(item));
		return items;

	}

}
