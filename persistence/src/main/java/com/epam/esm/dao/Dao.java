package com.epam.esm.dao;

import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;

import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {
    K create(T entity) throws CreateEntityException;

    Optional<T> findById(K id) throws EntityRetrievalException;

    void delete(T entity) throws DeleteEntityException;

    List<T> findAll() throws EntityRetrievalException;
}
