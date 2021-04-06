package com.epam.esm.service;

import com.epam.esm.exception.*;

import java.util.List;

/**
 * Base interface for application services
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface GiftService<T> {
    /**
     * Create entity
     *
     * @param entity an entity of business model
     * @return entity id
     * @throws CreateEntityException if error is occurred during SQL command execution
     */
    Long create(T entity) throws CreateResourceException;

    /**
     * Find entity
     *
     * @return entity
     * @throws EntityRetrievalException if fail to retrieve data from DB
     */
    T findById(Long id) throws ResourceNotFoundException;

    /**
     * Delete entity
     *
     * @throws DeleteEntityException if error is occurred during SQL command execution
     */
    void delete(T entity) throws DeleteResourceException, ResourceNotFoundException;

    /**
     * Find all entities
     *
     * @return List of entities
     * @throws EntityRetrievalException if fail to retrieve data from DB
     */
    List<T> findAll() throws ResourceNotFoundException;
}
