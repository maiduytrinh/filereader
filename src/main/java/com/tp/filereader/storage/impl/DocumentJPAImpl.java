package com.tp.filereader.storage.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Documents;
import com.tp.filereader.rdbms.GenericJPAImpl;
import com.tp.filereader.storage.DocumentStorage;

public class DocumentJPAImpl extends GenericJPAImpl<Documents, Integer> implements DocumentStorage{

	@Override
	public ResultContext<List<Documents>> getListDocumentOfCategory(String category, Integer limit) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Documents> query = cb.createQuery(modelClass);
			Root<Documents> entity = query.from(modelClass);
			//query
			Predicate getByCategory = cb.like(entity.get("categories"), "%," + category + ",%");
			Predicate getIsPublic = cb.notEqual(entity.get("isPublic"), "0");
			query.select(entity).where(cb.and(getByCategory, getIsPublic))
			.orderBy(cb.asc(entity.get("trendOrder")), cb.desc(entity.get("createdDate")));
			//set limit
			TypedQuery<Documents> typedQuery = getEntityManager().createQuery(query);
			if (limit > 0) {
				typedQuery.setMaxResults((int) limit);
			}
			List<Documents> result = typedQuery.getResultList();
			if (result != null && !result.isEmpty()) {
				return ResultContext.succeededResult(result);
			} else {
				return ResultContext.notFoundResult("The data is not found.");
			}
		} catch (NoResultException eex) {
			return ResultContext.notFoundResult(eex);
		} catch (RuntimeException e) {
			return ResultContext.failedResult(e);
		}
	}

}
