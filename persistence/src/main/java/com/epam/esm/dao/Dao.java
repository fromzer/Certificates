package com.epam.esm.dao;

import com.epam.esm.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {
    K create(T entity);

    Optional<T> findById(K id);

    void delete(T entity);

    List<T> findAll();
}
