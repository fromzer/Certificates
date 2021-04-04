package com.epam.esm.service;

import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

public interface GiftService<T> {
    Long create(T entity) throws CreateResourceException;

    T findById(Long id) throws ResourceNotFoundException;

    void delete(T entity) throws DeleteResourceException, ResourceNotFoundException;

    List<T> findAll() throws ResourceNotFoundException;
}
