package com.tp.filereader.rdbms.api;

import javax.persistence.EntityManager;

import com.tp.filereader.common.ResultContext;

import java.io.Serializable;
import java.util.List;

public interface GenericJPA<E, ID extends Serializable> {
	
	/**
	 * Get the number of entities with the specified type and id from the
	 * datasource.
	 *
	 * @return the entity, null if none is found
	 */
	Long count();

	/**
	 * Return an entity which is associated with specified id.
	 *
	 * @return an entity. Otherwise, return NULL.
	 */
	ResultContext<E> find(ID id);

	/**
	 * Get a list by give offset and limit
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<E> find(int offset, int limit);
	
	/**
	 * Get list by pageable
	 * 
	 * @param pageable
	 * 
	 * @return
	 */
	Pageable<E> getPageable(Pageable<E> pageable);

	/**
	 * Get a list of all object of the specified type from the datasource.
	 *
	 * @return a list of entities
	 */
	List<E> findAll();

	/**
	 * Insert a new entity. If the entity already exist, use update(E entity)
	 * instead
	 *
	 * @return the new entity
	 */
	ResultContext<E> create(E entity);

	/**
	 * Insert a list of new entities in the persistence context. If the entities
	 * already exist, use update(E entity) instead
	 */
	void createAll(List<E> entities);

	/**
	 * Update the entity in the persistence context. If the entity does not already
	 * exist, use create(E entity) instead
	 *
	 * @return the just created entity
	 */
	ResultContext<E> update(E entity);

	/**
	 * Update the entity in the persistence context. If the entity does not already
	 * exist, use create(E entity) instead
	 */
	void updateAll(List<E> entities);

	/**
	 * Delete the specified entity from the persistence context. Note: Sure to find
	 * Entity first, and, next delete. if No, there is exception
	 */
	void delete(E entity);

	/**
	 * Delete the specified ID from the persistence context.
	 */
	void delete(ID id);

	/**
	 * Remove all of the specified entities from the persistence context.
	 */
	void deleteAll(List<E> entities);

	/**
	 * Remove all of the entities from the persistence context.
	 */
	void deleteAll();
	
	/**
	 * Gets Entity Manager
	 * @return
	 */
	EntityManager getEntityManager();

}