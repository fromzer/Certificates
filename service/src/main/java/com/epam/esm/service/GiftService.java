package com.epam.esm.service;

import java.util.List;
import java.util.Optional;

public interface GiftService<T> {
    Long create(T entity);

    Optional<T> findById(Long id);

    void delete(T entity);

    List<T> findAll();
}
