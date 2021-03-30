package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GiftService<T> {
    Long create(T entity) throws ServiceException;

    Optional<T> findById(Long id) throws ServiceException;

    void delete(T entity) throws ServiceException;

    List<T> findAll() throws ServiceException;
}
