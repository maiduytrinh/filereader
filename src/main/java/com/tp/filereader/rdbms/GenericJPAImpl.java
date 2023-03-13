package com.tp.filereader.rdbms;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.rdbms.api.GenericJPA;
import com.tp.filereader.rdbms.api.Pageable;

public class GenericJPAImpl<E, ID extends Serializable> implements GenericJPA<E, ID> {

	protected Class<E> modelClass;

	protected static final Logger LOG = LoggerFactory.getLogger(GenericJPAImpl.class);

	public GenericJPAImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.modelClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
	}


	@Override
	public Long count() {
		boolean begunSession = beginSession();
		try {
			EntityManager em = getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> query = cb.createQuery(Long.class);

			Root<E> entity = query.from(modelClass);

			// Selecting the count
			query.select(cb.count(entity));
			return getEntityManager().createQuery(query).getSingleResult();
		} finally {
			closeSession(begunSession);
		}

	}

	@Override
	public Pageable<E> getPageable(Pageable<E> pageable) {
		boolean begunSession = beginSession();
		try {
			List<E> list = find(pageable.getOffset(), pageable.getLimit());
			final Pageable<E> result = new PageImpl<>(pageable.getOffset(), pageable.getLimit());
			result.setList(list);
			return result;

		} finally {
			closeSession(begunSession);
		}

	}

	@Override
	public List<E> find(int offset, int limit) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> query = cb.createQuery(modelClass);

		Root<E> entity = query.from(modelClass);

		// Selecting the entity
		query.select(entity);

		TypedQuery<E> typedQuery = getEntityManager().createQuery(query);

		if (limit > 0) {
			typedQuery.setFirstResult((int) offset);
			typedQuery.setMaxResults((int) limit);
		}

		return typedQuery.getResultList();
	}

	@Override
	public ResultContext<E> find(ID id) {
		try {
			EntityManager em = getEntityManager();
			final E result = (E) em.find(modelClass, id);
			if (result != null) {
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

	/**
	 * This method makes 2 calls to getEntityManager(): 1- The first one to get the
	 * CriteriaBuilder 2- The second one to create the query If there is no
	 * EntityManager in the threadLocal (i.e: EntityManagerHelp.getEntityManager()
	 * returns null), the EntityManagerHelp will return 2 distinct EntityManager
	 * instances. This will result in a org.hibernate.SessionException: Session is
	 * closed!.
	 *
	 * Thus, this method shall always be invoked with an EntityManager in the
	 * ThreadLocal
	 */
	@Override
	public List<E> findAll() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> query = cb.createQuery(modelClass);

		Root<E> entity = query.from(modelClass);

		// Selecting the entity
		query.select(entity);

		return getEntityManager().createQuery(query).getResultList();
	}

	@Override
	public ResultContext<E> create(E entity) {
		EntityManager em = getEntityManager();
		try {
			em.persist(entity);
			em.flush();
			return ResultContext.succeededResult(entity);

		} catch(PersistenceException e) {
			//get SQL Exception by e.getCause().getCause()
			if (e.getCause() != null && e.getCause().getCause() != null) {
				return ResultContext.failedResult(e.getCause().getCause());
			} else {
				return ResultContext.failedResult(e);
			}
		} catch (RuntimeException e) {
			return ResultContext.failedResult(e);
		}
	}

	@Override
	public void createAll(List<E> entities) {
		EntityManager em = getEntityManager();
		try {
			for (E entity : entities) {
				em.persist(entity);
			}
		} catch (RuntimeException e) {
			throw e;
		}

	}

	@Override
	public ResultContext<E> update(E entity) {
		EntityManager em = getEntityManager();
		try {
			entity = em.merge(entity);
			em.flush();
			return ResultContext.succeededResult(entity);
		} catch (EntityNotFoundException e) {
			return ResultContext.notFoundResult(e);
		} catch(PersistenceException e) {
			//get SQL Exception by e.getCause().getCause()
			if (e.getCause() != null && e.getCause().getCause() != null) {
				return ResultContext.failedResult(e.getCause().getCause());
			} else {
				return ResultContext.failedResult(e);
			}
		} catch (RuntimeException e) {
			return ResultContext.failedResult(e);
		}
	}

	@Override
	public void updateAll(List<E> entities) {
		EntityManager em = getEntityManager();
		try {
			for (E entity : entities) {
				em.merge(entity);
			}
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void delete(E entity) {
		EntityManager em = getEntityManager();
		try {
			em.remove(em.merge(entity));
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void delete(ID id) {
		EntityManager em = getEntityManager();
		try {
			ResultContext<E> resultContext = find(id);

			if (resultContext.succeeded()) {
				em.remove(resultContext.result());
			}
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void deleteAll(List<E> entities) {
		EntityManager em = getEntityManager();
		try {
			for (E entity : entities) {
				em.remove(entity);
			}
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void deleteAll() {
		List<E> entities = findAll();
		EntityManager em = getEntityManager();
		try {
			for (E entity : entities) {
				em.remove(entity);
			}
		} catch (RuntimeException e) {
			throw e;
		}
	}

	/**
	 * Return an EntityManager instance.
	 * 
	 * @return An EntityManger instance.
	 */
	public EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	public boolean beginSession() {
		EntityManagerHelper.getEntityManager();
		return EntityManagerHelper.isCreateNewSession();
	}


	public void closeSession(boolean begunSession) {
		EntityManagerHelper.closeSession(begunSession);
	}

}
