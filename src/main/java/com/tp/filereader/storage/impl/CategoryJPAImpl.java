package com.tp.filereader.storage.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Category;
import com.tp.filereader.rdbms.GenericJPAImpl;
import com.tp.filereader.storage.CategoryStorage;

public class CategoryJPAImpl extends GenericJPAImpl<Category, Integer> implements CategoryStorage {

	@Override
	public ResultContext<List<Category>> getListCategory(String country) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Category> query = cb.createQuery(modelClass);
			Root<Category> entity = query.from(modelClass);
			Predicate getByCountry = cb.like(entity.get("country"), country);
			Predicate getIsPublic = cb.equal(entity.get("isPublic"), "1");
			Predicate getByDocumentCount = cb.gt(entity.get("documentCount"), 0);
			query.select(entity).where(cb.and(getByCountry, getIsPublic, getByDocumentCount))
			.orderBy(cb.asc(entity.get("catOrder")), cb.desc(entity.get("createdDate")));
			List<Category> result = getEntityManager().createQuery(query).getResultList();
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
