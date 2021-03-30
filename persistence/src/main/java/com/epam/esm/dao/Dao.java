package com.epam.esm.dao;

import com.epam.esm.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {
    K create(T entity) throws DAOException;

    Optional<T> findById(K id) throws DAOException;

    void delete(T entity) throws DAOException;

    List<T> findAll() throws DAOException;
}
