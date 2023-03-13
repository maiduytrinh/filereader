package com.tp.filereader.storage.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.tp.filereader.entity.Documents;
import com.tp.filereader.rdbms.GenericJPAImpl;
import com.tp.filereader.rdbms.PageImpl;
import com.tp.filereader.rdbms.api.Pageable;
import com.tp.filereader.storage.DataStorage;

public class DataJPAImpl extends GenericJPAImpl<Documents, Integer> implements DataStorage {

	@Override
	public Pageable<Documents> getPageableByCountry(Pageable<Documents> pageable, String country) {
		// TODO Auto-generated method stub
		boolean begunSession = beginSession();
		try {
			List<Documents> list = findByCountry(country, pageable.getOffset(), pageable.getLimit());
			LOG.info("getPageableByCountry {}, list size {} , offset {}, limit {}", country, list.size(), pageable.getOffset(), pageable.getLimit());
			final Pageable<Documents> result = new PageImpl<>(pageable.getOffset(), pageable.getLimit());
			result.setList(list);
			return result;

		} finally {
			closeSession(begunSession);
		}
	}
	
	public List<Documents> findByCountry(String country, int offset, int limit) {
		try {
			EntityManager entityManager = getEntityManager();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Documents> criteriaQuery = criteriaBuilder.createQuery(Documents.class);
			Root<Documents> root = criteriaQuery.from(Documents.class);

			Predicate countryPredicate = criteriaBuilder.equal(root.get("country"), country);
			Predicate isPublicPredicate = criteriaBuilder.equal(root.get("isPublic"), "1");

			criteriaQuery.where(criteriaBuilder.and(countryPredicate, isPublicPredicate));
			List<Documents> documents = entityManager.createQuery(criteriaQuery).getResultList();
			return documents;
		} catch (Exception eex) {
			LOG.info("error get list Document with country {} and isPublic = 1");
		} 
		return new ArrayList<Documents>();
	}
}
