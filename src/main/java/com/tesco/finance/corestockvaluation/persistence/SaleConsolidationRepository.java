/**
 * 
 */
package com.tesco.finance.corestockvaluation.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tesco.finance.corestockvaluation.domain.SaleConsolidation;

/**
 * @author BARATH
 *
 */
public class SaleConsolidationRepository extends SimpleJpaRepository<SaleConsolidation, String> {

	private EntityManager entityManager;

	public SaleConsolidationRepository(EntityManager entityManager) {
		super(SaleConsolidation.class, entityManager);
		this.entityManager = entityManager;
	}

	@Transactional
	public List<SaleConsolidation> save(List<SaleConsolidation> items) {
		items.forEach(item -> entityManager.persist(item));
		return items;

	}

}
