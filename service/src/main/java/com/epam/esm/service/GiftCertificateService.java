package com.epam.esm.service;

import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateEntityException;
import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

/**
 * Base interface for Certificates
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface GiftCertificateService extends GiftService<GiftCertificate> {
    /**
     * Update entity
     *
     * @param certificateDTO an DTO of business model
     * @return updated GiftCertificate
     * @throws UpdateEntityException if fail to update data
     */
    GiftCertificate update(GiftCertificate certificateDTO) throws UpdateResourceException;

    /**
     * Find entity
     *
     * @param options the search options
     * @return list of GiftCertificates
     * @throws EntityRetrievalException if fail to retrieve data
     */
    List<GiftCertificate> findCertificateByParams(Map<String, String> params) throws ResourceNotFoundException;
}
